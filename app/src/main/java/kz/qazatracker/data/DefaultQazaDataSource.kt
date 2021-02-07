package kz.qazatracker.data

import android.content.SharedPreferences
import kz.qazatracker.qaza_input.data.*

private const val COMPLETED_QAZA_PERCENT_KEY = "completed_qaza_percent"
private const val TOTAL_PREYED_QAZA_COUNT_KEY = "total_preyed_qaza_count"

class DefaultQazaDataSource(
    private val sharedPreferences: SharedPreferences
): QazaDataSource {

    override fun saveQazaList(qazaDataList: List<QazaData>) {
        for (qazaData: QazaData in qazaDataList) {
            sharedPreferences.edit().putInt(qazaData.solatKey, qazaData.solatCount).apply()
        }
    }

    override fun getQazaList(): List<QazaData> = listOf(
        getQaza(FAJR_KEY),
        getQaza(ZUHR_KEY),
        getQaza(ASR_KEY),
        getQaza(MAGRIB_KEY),
        getQaza(ISHA_KEY),
        getQaza(UTIR_KEY)
    )

    override fun getQaza(solatKey: String): QazaData {
        return  when(solatKey) {
            FAJR_KEY -> QazaData.Fajr(sharedPreferences.getInt(solatKey, 0))
            ZUHR_KEY -> QazaData.Zuhr(sharedPreferences.getInt(solatKey, 0))
            ASR_KEY -> QazaData.Asr(sharedPreferences.getInt(solatKey, 0))
            MAGRIB_KEY -> QazaData.Magrib(sharedPreferences.getInt(solatKey, 0))
            ISHA_KEY -> QazaData.Isha(sharedPreferences.getInt(solatKey, 0))
            UTIR_KEY -> QazaData.Utir(sharedPreferences.getInt(solatKey, 0))
            else -> QazaData.Undefined
        }
    }

    override fun getCompletedQazaPercent(): Float {
       return (getTotalPrayedCount().toFloat() * 100) / (getTotalPrayedCount().toFloat() + getTotalRemainCount().toFloat())
    }

    override fun getTotalPrayedCount(): Int =
        sharedPreferences.getInt(TOTAL_PREYED_QAZA_COUNT_KEY, 0)

    override fun saveTotalPreyedCount(count: Int) {
        sharedPreferences.edit().putInt(TOTAL_PREYED_QAZA_COUNT_KEY, count).apply()
    }

    override fun getTotalRemainCount(): Int {
        var count = 0
        getQazaList().forEach {
            count += it.solatCount
        }

        return count
    }
}