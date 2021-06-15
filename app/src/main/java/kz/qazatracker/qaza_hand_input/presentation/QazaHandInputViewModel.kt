package kz.qazatracker.qaza_hand_input.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.qaza_hand_input.data.QazaData
import kotlin.math.abs

class QazaHandInputViewModel(
    private val qazaHandInputState: QazaHandInputState,
    private val qazaDataSource: QazaDataSource
) : ViewModel() {

    private val qazaViewDataListLiveData = MutableLiveData<List<QazaData>>()
    private val qazaInputNavigationLiveData = MutableLiveData<QazaHandInputNavigation>()
    private val titleLiveData = MutableLiveData<String>()
    private val infoLiveData = MutableLiveData<String>()

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
                titleLiveData.value = "Қазаларды өзгерту"
                infoLiveData.value = "Қосымша қаза намазды енгізу үшін «+»\n" +
                        "батырмасын, ал өтелген қаза намазды\n" +
                        "енгізу үшін «-» батырмасын басыңыз"
            }
            QazaHandInputState.Start -> {
                qazaDataSource.clearQazaList()
                qazaViewDataListLiveData.value = qazaDataSource.getQazaList()
                titleLiveData.value = "Сіздің қаза намаздарыңыз"
            }
            QazaHandInputState.QazaAutoCalculateEdit,
            QazaHandInputState.None -> {
                qazaViewDataListLiveData.value = qazaDataSource.getQazaList()
                titleLiveData.value = "Сіздің қаза намаздарыңыз"
            }
        }
    }

    fun getQazaDataListLiveData(): LiveData<List<QazaData>> = qazaViewDataListLiveData

    fun getTitleLiveData(): LiveData<String> = titleLiveData

    fun getInfoLiveData(): LiveData<String> = infoLiveData

    fun getQazaInputNavigationLiveData(): LiveData<QazaHandInputNavigation> =
        qazaInputNavigationLiveData

    fun saveQaza(inputQazaDataList: List<QazaData>) {
        updateTotalPreyedCount(inputQazaDataList)
        val actualQazaList: List<QazaData> = qazaDataSource.getQazaList()
        actualQazaList.forEachIndexed { i, element ->
            element.solatCount += inputQazaDataList[i].solatCount
            element.saparSolatCount += inputQazaDataList[i].saparSolatCount
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