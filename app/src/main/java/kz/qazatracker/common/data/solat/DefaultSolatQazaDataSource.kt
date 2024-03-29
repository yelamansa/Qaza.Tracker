package kz.qazatracker.common.data.solat

import android.content.SharedPreferences
import kz.qazatracker.R
import kz.qazatracker.qaza_hand_input.data.QazaData
import kz.qazatracker.qaza_hand_input.data.SaparQazaData

const val FAJR_KEY = "fajr"
const val ZUHR_KEY = "zuhr"
const val ASR_KEY = "asr"
const val MAGRIB_KEY = "magrib"
const val ISHA_KEY = "isha"
const val UTIR_KEY = "utir"
const val FASTING_KEY = "fasting"

const val PRAYED_QAZA_COUNT_COUNT_FORMAT = "%s_prayed_qaza_count"
private const val QAZA_SAVED_KEY = "qaza_save"

class DefaultSolatQazaDataSource(
    private val sharedPreferences: SharedPreferences
) : SolatQazaDataSource {

    override fun saveQazaList(qazaDataList: List<QazaData>) {
        for (qazaData: QazaData in qazaDataList) {
            sharedPreferences.edit().putInt(qazaData.solatKey, qazaData.solatCount).apply()
            sharedPreferences.edit()
                .putInt(getSaparSolatKey(qazaData.solatKey), qazaData.saparSolatCount).apply()
        }
        sharedPreferences.edit().putBoolean(QAZA_SAVED_KEY, true).apply()
    }

    override fun getQazaList(): List<QazaData> = listOf(
        getQaza(FAJR_KEY, R.string.fajr, false),
        getQaza(ZUHR_KEY, R.string.zuhr, true),
        getQaza(ASR_KEY, R.string.asr, true),
        getQaza(MAGRIB_KEY, R.string.magrib, false),
        getQaza(ISHA_KEY, R.string.isha, true),
        getQaza(UTIR_KEY, R.string.utir, false),
            getQaza(FASTING_KEY, R.string.fasting, false)
    )

    override fun clearQazaList() {
        clearQaza(FAJR_KEY)
        clearQaza(ZUHR_KEY)
        clearQaza(ASR_KEY)
        clearQaza(MAGRIB_KEY)
        clearQaza(ISHA_KEY)
        clearQaza(UTIR_KEY)
        clearQaza(FASTING_KEY)
        sharedPreferences.edit().putBoolean(QAZA_SAVED_KEY, false).apply()
    }

    private fun getQaza(
        solatKey: String,
        solatNameResId: Int,
        hasSaparSolat: Boolean
    ): QazaData {
        val solatCount = sharedPreferences.getInt(solatKey, 0)
        val saparSolatCount =
            if (!hasSaparSolat) 0 else sharedPreferences.getInt(getSaparSolatKey(solatKey), 0)

        return QazaData(
                solatKey = solatKey,
                solatNameResId = solatNameResId,
                solatCount = solatCount,
                saparSolatCount = saparSolatCount,
                minSolatCount = -solatCount,
                minSaparSolatCount = -saparSolatCount,
                completedCount = getPrayedCount(solatKey) + getPrayedCount(getSaparSolatKey(solatKey)),
                hasSaparSolat = hasSaparSolat,
                saparSolatData = getSaparQazaData(solatKey, hasSaparSolat)
        )
    }

    private fun getSaparQazaData(
            key: String,
            hasSaparSolat: Boolean,
    ) = if (hasSaparSolat) {
        SaparQazaData(
                key = getSaparSolatKey(key),
                count = sharedPreferences.getInt(getSaparSolatKey(key), 0),
                minCount = -sharedPreferences.getInt(getSaparSolatKey(key), 0),
        )
    } else {
        null
    }

    override fun getTotalPrayedCount(): Int {
        var totalPrayedCount = 0
        for (qazaData in getQazaList()) {
            totalPrayedCount += getPrayedCount(qazaData.solatKey)
        }

        return totalPrayedCount
    }

    override fun getPrayedCount(
        solatKey: String
    ): Int = sharedPreferences.getInt(PRAYED_QAZA_COUNT_COUNT_FORMAT.format(solatKey), 0)

    override fun addAndSavePrayedCount(
        solatKey: String,
        count: Int
    ) {
        val actualPrayedCount = getPrayedCount(solatKey)
        val updatedPrayedCount = actualPrayedCount + count
        sharedPreferences.edit().putInt(
            PRAYED_QAZA_COUNT_COUNT_FORMAT.format(solatKey), updatedPrayedCount
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
        sharedPreferences.edit().putInt(PRAYED_QAZA_COUNT_COUNT_FORMAT.format(key), 0).apply()
        sharedPreferences.edit().putInt(PRAYED_QAZA_COUNT_COUNT_FORMAT.format(getSaparSolatKey(key)), 0).apply()
    }
}