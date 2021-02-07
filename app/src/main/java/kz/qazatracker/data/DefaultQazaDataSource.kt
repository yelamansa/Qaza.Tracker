package kz.qazatracker.data

import android.content.SharedPreferences
import kz.qazatracker.qaza_input.data.*

private const val TOTAL_PREYED_QAZA_COUNT_KEY = "total_prayed_qaza_count"
private const val PRAYED_QAZA_COUNT_COUNT_FORMAT = "%s_prayed_qaza_count"

class DefaultQazaDataSource(
    private val sharedPreferences: SharedPreferences
) : QazaDataSource {

    private val fajr: QazaData
    private val zuhr: QazaData
    private val asr: QazaData
    private val magrib: QazaData
    private val isha: QazaData
    private val utir: QazaData

    init {
        fajr = getQaza(FAJR_KEY)
        zuhr = getQaza(ZUHR_KEY)
        asr = getQaza(ASR_KEY)
        magrib = getQaza(MAGRIB_KEY)
        isha = getQaza(ISHA_KEY)
        utir = getQaza(UTIR_KEY)
    }

    override fun saveQazaList(qazaDataList: List<QazaData>) {
        for (qazaData: QazaData in qazaDataList) {
            sharedPreferences.edit().putInt(qazaData.solatKey, qazaData.solatCount).apply()
        }
    }

    override fun getQazaList(): List<QazaData> = listOf(
        fajr,
        zuhr,
        asr,
        magrib,
        isha,
        utir
    )

    override fun getQaza(solatKey: String): QazaData {
        return when (solatKey) {
            FAJR_KEY -> QazaData.Fajr(
                count = sharedPreferences.getInt(solatKey, 0),
                totalCount = getTotalPrayedCount(solatKey)
            )
            ZUHR_KEY -> QazaData.Zuhr(
                count = sharedPreferences.getInt(solatKey, 0),
                totalCount = getTotalPrayedCount(solatKey)
            )
            ASR_KEY -> QazaData.Asr(
                count = sharedPreferences.getInt(solatKey, 0),
                totalCount = getTotalPrayedCount(solatKey)
            )
            MAGRIB_KEY -> QazaData.Magrib(
                count = sharedPreferences.getInt(solatKey, 0),
                totalCount = getTotalPrayedCount(solatKey)
            )
            ISHA_KEY -> QazaData.Isha(
                count = sharedPreferences.getInt(solatKey, 0),
                totalCount = getTotalPrayedCount(solatKey)
            )
            UTIR_KEY -> QazaData.Utir(
                count = sharedPreferences.getInt(solatKey, 0),
                totalCount = getTotalPrayedCount(solatKey)
            )
            else -> QazaData.Undefined
        }
    }

    override fun getTotalCompletedQazaPercent(): Float {
        val totalCount = getTotalPrayedCount() + getTotalRemainCount()

        if (totalCount == 0) return 0f

        return (getTotalPrayedCount().toFloat() * 100) / totalCount
    }

    override fun getTotalPrayedCount(): Int =
        sharedPreferences.getInt(TOTAL_PREYED_QAZA_COUNT_KEY, 0)

    override fun getTotalPrayedCount(
        solatKey: String
    ): Int = sharedPreferences.getInt(PRAYED_QAZA_COUNT_COUNT_FORMAT.format(solatKey), 0)

    override fun saveTotalPreyedCount(
        count: Int
    ) {
        sharedPreferences.edit().putInt(TOTAL_PREYED_QAZA_COUNT_KEY, count).apply()
    }

    override fun saveTotalPrayedCount(
        solatKey: String,
        count: Int
    ) {
        sharedPreferences.edit().putInt(
            PRAYED_QAZA_COUNT_COUNT_FORMAT.format(solatKey),
            count
        ).apply()
    }

    override fun getTotalRemainCount(): Int {
        var count = 0
        getQazaList().forEach {
            count += it.solatCount
        }

        return count
    }
}