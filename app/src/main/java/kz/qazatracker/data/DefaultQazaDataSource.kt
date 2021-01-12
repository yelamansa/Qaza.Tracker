package kz.qazatracker.data

import android.content.SharedPreferences
import kz.qazatracker.qaza_input.data.QazaData

private const val FAJR_KEY = "fajr"
private const val ZUHR_KEY = "zuhr"
private const val ASR_KEY = "asr"
private const val MAGRIB_KEY = "magrib"
private const val ISHA_KEY = "isha"

class DefaultQazaDataSource(
    private val sharedPreferences: SharedPreferences
): QazaDataSource {

    override fun saveQaza(qazaData: QazaData) {
        sharedPreferences.edit().putInt(FAJR_KEY, qazaData.fajr).apply()
        sharedPreferences.edit().putInt(ZUHR_KEY, qazaData.zuhr).apply()
        sharedPreferences.edit().putInt(ASR_KEY, qazaData.asr).apply()
        sharedPreferences.edit().putInt(MAGRIB_KEY, qazaData.magrib).apply()
        sharedPreferences.edit().putInt(ISHA_KEY, qazaData.isha).apply()
    }

    override fun getQaza(): QazaData {
        val fajr = sharedPreferences.getInt(FAJR_KEY, 0)
        val zuhr = sharedPreferences.getInt(ZUHR_KEY, 0)
        val asr = sharedPreferences.getInt(ASR_KEY, 0)
        val magrib = sharedPreferences.getInt(MAGRIB_KEY, 0)
        val isha = sharedPreferences.getInt(ISHA_KEY, 0)

        return QazaData(
            fajr,
            zuhr,
            asr,
            magrib,
            isha,
            0
        )
    }
}