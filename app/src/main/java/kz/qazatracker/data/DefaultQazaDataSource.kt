package kz.qazatracker.data

import android.content.Context
import android.content.SharedPreferences
import kz.qazatracker.R
import kz.qazatracker.qaza_hand_input.data.*

const val FAJR_KEY = "fajr"
const val ZUHR_KEY = "zuhr"
const val ASR_KEY = "asr"
const val MAGRIB_KEY = "magrib"
const val ISHA_KEY = "isha"
const val UTIR_KEY = "utir"

private const val TOTAL_PREYED_QAZA_COUNT_KEY = "total_prayed_qaza_count"
private const val PRAYED_QAZA_COUNT_COUNT_FORMAT = "%s_prayed_qaza_count"
private const val QAZA_SAVED_KEY = "qaza_save"

class DefaultQazaDataSource(
    private val sharedPreferences: SharedPreferences,
    private val context: Context
) : QazaDataSource {

    override fun saveQazaList(qazaDataList: List<QazaData>) {
        for (qazaData: QazaData in qazaDataList) {
            sharedPreferences.edit().putInt(qazaData.solatKey, qazaData.solatCount).apply()
            sharedPreferences.edit()
                .putInt(getSaparSolatKey(qazaData.solatKey), qazaData.saparSolatCount).apply()
        }
        sharedPreferences.edit().putBoolean(QAZA_SAVED_KEY, true).apply()
    }

    override fun getQazaList(): List<QazaData> = listOf(
        getQaza(FAJR_KEY, context.getString(R.string.fajr), false),
        getQaza(ZUHR_KEY, context.getString(R.string.zuhr), true),
        getQaza(ASR_KEY, context.getString(R.string.asr), true),
        getQaza(MAGRIB_KEY, context.getString(R.string.magrib), false),
        getQaza(ISHA_KEY, context.getString(R.string.isha), true),
        getQaza(UTIR_KEY, context.getString(R.string.utir), false)
    )

    override fun clearQazaList() {
        clearQaza(FAJR_KEY)
        clearQaza(ZUHR_KEY)
        clearQaza(ASR_KEY)
        clearQaza(MAGRIB_KEY)
        clearQaza(ISHA_KEY)
        clearQaza(UTIR_KEY)
        sharedPreferences.edit().putBoolean(QAZA_SAVED_KEY, false).apply()
    }

    private fun getQaza(
        solatKey: String,
        solatName: String,
        hasSaparSolat: Boolean
    ): QazaData {
        val solatCount = sharedPreferences.getInt(solatKey, 0)
        val saparSolatCount =
            if (!hasSaparSolat) 0 else sharedPreferences.getInt(getSaparSolatKey(solatKey), 0)

        return QazaData(
            solatKey = solatKey,
            solatName = solatName,
            solatCount = solatCount,
            saparSolatCount = saparSolatCount,
            minSolatCount = -solatCount,
            minSaparSolatCount = -saparSolatCount,
            totalPrayedCount = getTotalPrayedCount(solatKey) + getTotalPrayedCount(
                getSaparSolatKey(
                    solatKey
                )
            ),
            hasSaparSolat = hasSaparSolat
        )
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
            count += it.saparSolatCount
        }

        return count
    }

    override fun isQazaSaved(): Boolean = sharedPreferences.getBoolean(QAZA_SAVED_KEY, false)

    private fun getSaparSolatKey(solatKey: String) = "${solatKey}_sapar"

    private fun clearQaza(key: String) {
        sharedPreferences.edit().remove(key).apply()
        sharedPreferences.edit().remove(getSaparSolatKey(key)).apply()
    }
}