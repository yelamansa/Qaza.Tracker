package kz.qazatracker.qaza_hand_input.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.R
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.qaza_hand_input.data.QazaData

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
                qazaViewDataListLiveData.value = qazaDataSource.getQazaList()
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
        val actualQazaList: List<QazaData> = qazaDataSource.getQazaList()
        actualQazaList.forEachIndexed { i, actualQazaData ->
            val updatedQazaData = inputQazaDataList[i]
            updateTotalPreyedCount(actualQazaData, updatedQazaData)
            actualQazaData.solatCount = updatedQazaData.solatCount
            actualQazaData.saparSolatCount = updatedQazaData.saparSolatCount
        }
        qazaDataSource.saveQazaList(actualQazaList)
        qazaInputNavigationLiveData.value = QazaHandInputNavigation.MainScreen
    }

    private fun updateTotalPreyedCount(
        actualQazaData: QazaData,
        updatedQazaData: QazaData
    ) {
        if (qazaHandInputState !is QazaHandInputState.QazaEdit) return

        var totalPreyedCount: Int = qazaDataSource.getTotalPrayedCount()
        val solatDiff = actualQazaData.solatCount - updatedQazaData.solatCount
        val saparSolatDiff = actualQazaData.saparSolatCount - updatedQazaData.saparSolatCount
        if (solatDiff > 0) {
            totalPreyedCount += solatDiff
            updateSolatTotalPrayedCount(updatedQazaData, solatDiff)
        }
        if (saparSolatDiff > 0) {
            totalPreyedCount += saparSolatDiff
            updateSaparSolatTotalPrayedCount(updatedQazaData, saparSolatDiff)
        }
        qazaDataSource.saveTotalPreyedCount(totalPreyedCount)
    }

    private fun updateSolatTotalPrayedCount(qazaData: QazaData, solatCount: Int) {
        val actualPrayedCount = qazaDataSource.getTotalPrayedCount(qazaData.solatKey)
        qazaDataSource.saveTotalPrayedCount(
            solatKey = qazaData.solatKey,
            count = actualPrayedCount + solatCount
        )
    }

    private fun updateSaparSolatTotalPrayedCount(qazaData: QazaData, saparSolatCount: Int) {
        val actualPrayedCount: Int = qazaDataSource.getTotalPrayedCount(
            getSaparSolatName(qazaData.solatKey)
        )
        qazaDataSource.saveTotalPrayedCount(
            solatKey = getSaparSolatName(qazaData.solatKey),
            count = actualPrayedCount + saparSolatCount
        )
    }

    private fun getSaparSolatName(solatKey: String) = "${solatKey}_sapar"
}