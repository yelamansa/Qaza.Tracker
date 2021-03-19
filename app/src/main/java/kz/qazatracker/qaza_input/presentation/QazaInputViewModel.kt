package kz.qazatracker.qaza_input.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.qaza_input.data.QazaData
import kotlin.math.abs

class QazaInputViewModel(
    private val qazaInputState: QazaInputState,
    private val qazaDataSource: QazaDataSource
) : ViewModel() {

    private val qazaViewDataListLiveData = MutableLiveData<List<QazaData>>()
    private val qazaInputNavigationLiveData = MutableLiveData<QazaInputNavigation>()

    init {
        when (qazaInputState) {
            QazaInputState.Reduction -> {
                qazaViewDataListLiveData.value = qazaDataSource.getQazaList().map {
                    it.also {
                        it.minSolatCount = -it.solatCount
                        it.minSaparSolatCount = -it.saparSolatCount
                    }
                }
            }
            QazaInputState.Correction,
            QazaInputState.Start,
            QazaInputState.None -> {
                qazaViewDataListLiveData.value = qazaDataSource.getQazaList()
            }
        }
    }

    fun getQazaDataListLiveData(): LiveData<List<QazaData>> = qazaViewDataListLiveData

    fun getQazaInputNavigationLiveData(): LiveData<QazaInputNavigation> =
        qazaInputNavigationLiveData

    fun saveQaza(inputQazaDataList: List<QazaData>) {
        updateTotalPreyedCount(inputQazaDataList)
        val actualQazaList: List<QazaData> = qazaDataSource.getQazaList()
        actualQazaList.forEachIndexed { i, element ->
            element.solatCount += inputQazaDataList[i].solatCount
        }
        qazaDataSource.saveQazaList(actualQazaList)
        qazaInputNavigationLiveData.value = QazaInputNavigation.MainScreen
    }

    private fun updateTotalPreyedCount(inputQazaDataList: List<QazaData>) {
        var totalPreyedCount: Int = qazaDataSource.getTotalPrayedCount()
        inputQazaDataList.forEach { qazaData ->
            if (qazaData.solatCount < 0) {
                totalPreyedCount += abs(qazaData.solatCount)
                updateSolatTotalPrayedCount(qazaData)
            }
        }
        qazaDataSource.saveTotalPreyedCount(totalPreyedCount)
    }

    private fun updateSolatTotalPrayedCount(qazaData: QazaData) {
        val actualPrayedCount = qazaDataSource.getTotalPrayedCount(qazaData.solatKey)
        qazaDataSource.saveTotalPrayedCount(
            qazaData.solatKey, actualPrayedCount + abs(qazaData.solatCount)
        )
    }
}