package kz.qazatracker.qaza_hand_input.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.R
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.qaza_hand_input.data.QazaData
import kotlin.math.abs

class QazaHandInputViewModel(
    private val qazaHandInputState: QazaHandInputState,
    private val qazaDataSource: QazaDataSource
) : ViewModel() {

    private val qazaViewDataListLiveData = MutableLiveData<List<QazaData>>()
    private val qazaInputNavigationLiveData = MutableLiveData<QazaHandInputNavigation>()
    private val titleLiveData = MutableLiveData<Int>()
    private val infoLiveData = MutableLiveData<Int>()

    fun onCreate() {
        when (qazaHandInputState) {
            QazaHandInputState.QazaEdit -> {
                qazaViewDataListLiveData.value = qazaDataSource.getQazaList().map {
                    it.also {
                        it.minSolatCount = -it.solatCount
                        it.minSaparSolatCount = -it.saparSolatCount
                        it.solatCount = 0
                        it.saparSolatCount = 0
                    }
                }
                titleLiveData.value = R.string.change_qaza
                infoLiveData.value = R.string.info_change_qaza
            }
            QazaHandInputState.Start -> {
                qazaDataSource.clearQazaList()
                qazaViewDataListLiveData.value = qazaDataSource.getQazaList()
                titleLiveData.value = R.string.your_qaza_solats
            }
            QazaHandInputState.QazaAutoCalculateEdit,
            QazaHandInputState.None -> {
                qazaViewDataListLiveData.value = qazaDataSource.getQazaList()
                titleLiveData.value = R.string.your_qaza_solats
            }
        }
    }

    fun getQazaDataListLiveData(): LiveData<List<QazaData>> = qazaViewDataListLiveData

    fun getTitleLiveData(): LiveData<Int> = titleLiveData

    fun getInfoLiveData(): LiveData<Int> = infoLiveData

    fun getQazaInputNavigationLiveData(): LiveData<QazaHandInputNavigation> =
        qazaInputNavigationLiveData

    fun saveQaza(inputQazaDataList: List<QazaData>) {
        updateTotalPreyedCount(inputQazaDataList)
        val actualQazaList: List<QazaData> = qazaDataSource.getQazaList()
        actualQazaList.forEachIndexed { i, element ->
            when (qazaHandInputState) {
                QazaHandInputState.QazaEdit -> {
                    element.solatCount += inputQazaDataList[i].solatCount
                    element.saparSolatCount += inputQazaDataList[i].saparSolatCount
                }
                else -> {
                    element.solatCount = inputQazaDataList[i].solatCount
                    element.saparSolatCount = inputQazaDataList[i].saparSolatCount
                }
            }
        }
        qazaDataSource.saveQazaList(actualQazaList)
        qazaInputNavigationLiveData.value = QazaHandInputNavigation.MainScreen
    }

    private fun updateTotalPreyedCount(inputQazaDataList: List<QazaData>) {
        var totalPreyedCount: Int = qazaDataSource.getTotalPrayedCount()
        inputQazaDataList.forEach { qazaData ->
            if (qazaData.solatCount < 0) {
                totalPreyedCount += abs(qazaData.solatCount)
                updateSolatTotalPrayedCount(qazaData)
            }
            if (qazaData.saparSolatCount < 0) {
                updateSaparSolatTotalPrayedCount(qazaData)
                totalPreyedCount += abs(qazaData.saparSolatCount)
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

    private fun updateSaparSolatTotalPrayedCount(qazaData: QazaData) {
        val actualPrayedCount: Int = qazaDataSource.getTotalPrayedCount(
            getSaparSolatName(qazaData.solatKey)
        )
        qazaDataSource.saveTotalPrayedCount(
            solatKey = getSaparSolatName(qazaData.solatKey),
            count = actualPrayedCount + abs(qazaData.saparSolatCount)
        )
    }

    private fun getSaparSolatName(solatKey: String) = "${solatKey}_sapar"
}