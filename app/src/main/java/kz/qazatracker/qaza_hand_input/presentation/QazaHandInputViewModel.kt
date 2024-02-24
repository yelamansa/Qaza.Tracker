package kz.qazatracker.qaza_hand_input.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.R
import kz.qazatracker.common.data.solat.SolatQazaDataSource
import kz.qazatracker.qaza_hand_input.data.QazaData

class QazaHandInputViewModel(
    private val qazaHandInputState: QazaHandInputState,
    private val solatQazaDataSource: SolatQazaDataSource
) : ViewModel() {

    private val qazaViewDataListLiveData = MutableLiveData<List<QazaData>>()
    private val qazaInputNavigationLiveData = MutableLiveData<QazaHandInputNavigation>()
    private val titleLiveData = MutableLiveData<Int>()
    private val infoLiveData = MutableLiveData<Int>()

    fun onCreate() {
        when (qazaHandInputState) {
            QazaHandInputState.QazaEdit -> {
                qazaViewDataListLiveData.value = solatQazaDataSource.getQazaList()
                titleLiveData.value = R.string.change_qaza
                infoLiveData.value = R.string.info_change_qaza
            }
            QazaHandInputState.Start -> {
                solatQazaDataSource.clearQazaList()
                qazaViewDataListLiveData.value = solatQazaDataSource.getQazaList()
                titleLiveData.value = R.string.your_qaza_solats
            }
            QazaHandInputState.QazaAutoCalculateEdit,
            QazaHandInputState.None -> {
                qazaViewDataListLiveData.value = solatQazaDataSource.getQazaList()
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
        val actualQazaList: List<QazaData> = solatQazaDataSource.getQazaList()
        actualQazaList.forEachIndexed { i, actualQazaData ->
            val updatedQazaData = inputQazaDataList[i]
            updateTotalPreyedCount(actualQazaData, updatedQazaData)
            actualQazaData.solatCount = updatedQazaData.solatCount
            actualQazaData.saparSolatCount = updatedQazaData.saparSolatCount
        }
        solatQazaDataSource.saveQazaList(actualQazaList)
        qazaInputNavigationLiveData.value = QazaHandInputNavigation.MainScreen
    }

    private fun updateTotalPreyedCount(
        actualQazaData: QazaData,
        updatedQazaData: QazaData
    ) {
        if (qazaHandInputState !is QazaHandInputState.QazaEdit) return

        var totalPreyedCount: Int = solatQazaDataSource.getTotalPrayedCount()
        val solatDiff = actualQazaData.solatCount - updatedQazaData.solatCount
        val saparSolatDiff = actualQazaData.saparSolatCount - updatedQazaData.saparSolatCount
        if (solatDiff > 0) {
            totalPreyedCount += solatDiff
            updateSolatPrayedCount(updatedQazaData, solatDiff)
        }
        if (saparSolatDiff > 0) {
            totalPreyedCount += saparSolatDiff
            updateSaparSolatPrayedCount(updatedQazaData, saparSolatDiff)
        }
    }

    private fun updateSolatPrayedCount(qazaData: QazaData, solatCount: Int) {
        val actualPrayedCount = solatQazaDataSource.getPrayedCount(qazaData.solatKey)
        solatQazaDataSource.addAndSavePrayedCount(
            solatKey = qazaData.solatKey,
            count = actualPrayedCount + solatCount
        )
    }

    private fun updateSaparSolatPrayedCount(qazaData: QazaData, saparSolatCount: Int) {
        val actualPrayedCount: Int = solatQazaDataSource.getPrayedCount(
            getSaparSolatName(qazaData.solatKey)
        )
        solatQazaDataSource.addAndSavePrayedCount(
            solatKey = getSaparSolatName(qazaData.solatKey),
            count = actualPrayedCount + saparSolatCount
        )
    }

    private fun getSaparSolatName(solatKey: String) = "${solatKey}_sapar"
}