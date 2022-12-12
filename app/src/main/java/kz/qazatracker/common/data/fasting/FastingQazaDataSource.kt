package kz.qazatracker.common.data.fasting

import android.content.SharedPreferences

const val FASTING_QAZA = "fasting_qaza"

class FastingQazaDataSource(
    private val sharedPreferences: SharedPreferences
) {

    fun getFastingQazaData(): Int =
        sharedPreferences.getInt(FASTING_QAZA, 0)
}