package kz.qazatracker.common.data.fasting

import android.content.SharedPreferences
import kz.qazatracker.common.data.solat.PRAYED_QAZA_COUNT_COUNT_FORMAT

const val FASTING_QAZA = "fasting"

class FastingQazaDataSource(
    private val sharedPreferences: SharedPreferences
) {

    fun getFastingQazaData(): Int =
        sharedPreferences.getInt(FASTING_QAZA, 0)

    fun getFastingQazaCompletedCount(): Int = sharedPreferences.getInt(
            PRAYED_QAZA_COUNT_COUNT_FORMAT.format(FASTING_QAZA),
            0
    )
}