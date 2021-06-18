package kz.qazatracker.main.qaza_progress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.qaza_hand_input.data.QazaData
import kotlin.math.ceil
import kotlin.math.roundToInt

class QazaProgressViewModel(
    private val qazaDataSource: QazaDataSource
) : ViewModel() {

    private val qazaLiveData = MutableLiveData<List<QazaData>>()
    private val qazaProgressLiveData = MutableLiveData<QazaProgressData>()
    private val calculatedRemainTimeLiveData = MutableLiveData<String>()

    fun onCreate() {
        val qazaDataList = qazaDataSource.getQazaList()
        qazaLiveData.value = qazaDataList
        val qazaProgressData = QazaProgressData(
            completedPercent = qazaDataSource.getTotalCompletedQazaPercent(),
            totalPreyedCount = qazaDataSource.getTotalPrayedCount(),
            totalRemainCount = qazaDataSource.getTotalRemainCount()
        )
        qazaProgressLiveData.value = qazaProgressData
    }

    fun getQazaLiveData() = qazaLiveData

    fun getQazaProgressLiveData() = qazaProgressLiveData

    fun getCalculatedRemainTime() = calculatedRemainTimeLiveData

    fun calculateRemainDate(solatCountPerDay: Int) {
        if (solatCountPerDay == 0) return

        val totalRemainSolatCount = qazaDataSource.getTotalRemainCount()
        val day = ceil(totalRemainSolatCount / solatCountPerDay.toDouble())
        calculatedRemainTimeLiveData.value = "${day.roundToInt()} күнде толық оқып боласыз"
    }
}