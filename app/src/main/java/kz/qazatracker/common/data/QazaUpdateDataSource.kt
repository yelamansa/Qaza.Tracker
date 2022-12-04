package kz.qazatracker.common.data

import android.content.SharedPreferences

class QazaUpdateDataSource(
    private val sharedPreferences: SharedPreferences
) {

    fun increaseQazaValue(
        solatKey: String
    ) {
        var currentSolatCount = sharedPreferences.getInt(solatKey, 0)
        sharedPreferences.edit().putInt(solatKey, ++currentSolatCount).apply()
    }

    fun decreaseQazaValue(solatKey: String) {
        var currentSolatCount = sharedPreferences.getInt(solatKey, 0)
        if (currentSolatCount == 0) return

        sharedPreferences.edit().putInt(solatKey, --currentSolatCount).apply()
    }
}