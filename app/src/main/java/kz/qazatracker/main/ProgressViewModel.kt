package kz.qazatracker.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.qaza_input.data.QazaData

class ProgressViewModel(
    private val qazaDataSource: QazaDataSource
) : ViewModel() {

    private val qazaLiveData = MutableLiveData<List<QazaData>>()
    private val qazaProgressLiveData = MutableLiveData<QazaProgressData>()

    fun onCreate() {
        val qazaDataList = qazaDataSource.getQazaList()
        qazaLiveData.value = qazaDataList
        val qazaProgressData = QazaProgressData(
            completedPercent = qazaDataSource.getCompletedQazaPercent(),
            totalPreyedCount = qazaDataSource.getTotalPrayedCount(),
            totalRemainCount = qazaDataSource.getTotalRemainCount()
        )
        qazaProgressLiveData.value = qazaProgressData
    }

    fun getQazaLiveData() = qazaLiveData

    fun getQazaProgressLiveData() = qazaProgressLiveData
}