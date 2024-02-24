package kz.qazatracker.common.data

import android.content.SharedPreferences
import kz.qazatracker.common.data.solat.SolatQazaDataSource

class QazaUpdateDataSource(
        private val sharedPreferences: SharedPreferences,
        private val solatQazaDataSource: SolatQazaDataSource,
) {

    fun increaseQazaValue(
            qazaKey: String
    ) {
        var currentSolatCount = sharedPreferences.getInt(qazaKey, 0)
        sharedPreferences.edit().putInt(qazaKey, ++currentSolatCount).apply()
    }

    fun decreaseQazaValue(qazaKey: String) {
        var currentSolatCount = sharedPreferences.getInt(qazaKey, 0)
        if (currentSolatCount == 0) return

        sharedPreferences.edit().putInt(qazaKey, --currentSolatCount).apply()
        solatQazaDataSource.addAndSavePrayedCount(qazaKey, 1)
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