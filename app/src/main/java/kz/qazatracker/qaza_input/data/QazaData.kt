package kz.qazatracker.qaza_input.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

const val FAJR_KEY = "fajr"
const val ZUHR_KEY = "zuhr"
const val ASR_KEY = "asr"
const val MAGRIB_KEY = "magrib"
const val ISHA_KEY = "isha"
const val UTIR_KEY = "utir"
const val FAJR_NAME = "Таң"
const val ZUHR_NAME = "Бесін"
const val ASR_NAME = "Аср"
const val MAGRIB_NAME = "Шам"
const val ISHA_NAME = "Құптан"
const val UTIR_NAME = "Үтір"

sealed class QazaData(
    val solatKey: String,
    val solatName: String,
    var solatCount: Int,
    val totalPrayedCount: Int
): Parcelable {

    @Parcelize
    data class Fajr(
        val count: Int,
        val totalCount: Int = 0
    ): QazaData(FAJR_KEY, FAJR_NAME, count, totalCount)

    @Parcelize
    data class Zuhr(
        val count: Int,
        val totalCount: Int = 0
    ): QazaData(ZUHR_KEY, ZUHR_NAME, count, totalCount)

    @Parcelize
    data class Asr(
        val count: Int,
        val totalCount: Int = 0
    ): QazaData(ASR_KEY, ASR_NAME, count, totalCount)

    @Parcelize
    data class Magrib(
        val count: Int,
        val totalCount: Int = 0
    ): QazaData(MAGRIB_KEY, MAGRIB_NAME, count, totalCount)

    @Parcelize
    data class Isha(
        val count: Int,
        val totalCount: Int = 0
    ): QazaData(ISHA_KEY, ISHA_NAME, count, totalCount)

    @Parcelize
    data class Utir(
        val count: Int,
        val totalCount: Int = 0
    ): QazaData(UTIR_KEY, UTIR_NAME, count, totalCount)

    @Parcelize
    object Undefined : QazaData("", "", 0, 0)

    fun getTotalCompletedQazaPercent(
    ): Float{

        val totalCount = totalPrayedCount + solatCount

        if (totalCount == 0) return 0f

        return totalPrayedCount.toFloat() * 100 / totalCount
    }
}