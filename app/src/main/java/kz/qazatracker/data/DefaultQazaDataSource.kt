package kz.qazatracker.data

import android.content.SharedPreferences
import kz.qazatracker.qaza_input.data.*

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
            else -> QazaData.UndefinedQazaData
        }
    }
}