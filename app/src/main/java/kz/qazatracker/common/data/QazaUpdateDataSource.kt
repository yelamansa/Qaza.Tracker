package kz.qazatracker.common.data

import android.content.SharedPreferences

class QazaUpdateDataSource(
    private val sharedPreferences: SharedPreferences
) {

    fun increaseQazaValue(
        qazaKeyKey: String
    ) {
        var currentSolatCount = sharedPreferences.getInt(qazaKeyKey, 0)
        sharedPreferences.edit().putInt(qazaKeyKey, ++currentSolatCount).apply()
    }

    fun decreaseQazaValue(solatKey: String) {
        var currentSolatCount = sharedPreferences.getInt(solatKey, 0)
        if (currentSolatCount == 0) return

        sharedPreferences.edit().putInt(solatKey, --currentSolatCount).apply()
    }

    fun updateQazaValue(
        qazaKey: String,
        value: Int
    ) {
        val currentQazaCount = sharedPreferences.getInt(qazaKey, 0)
        val updatedQazaCount = currentQazaCount + value
        if (updatedQazaCount < 0) return

        sharedPreferences.edit().putInt(qazaKey, updatedQazaCount).apply()
    }
}