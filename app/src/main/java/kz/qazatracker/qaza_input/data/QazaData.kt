package kz.qazatracker.qaza_input.data

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
    val solatCount: Int
) {

    data class Fajr(val count: Int): QazaData(FAJR_KEY, FAJR_NAME, count)
    data class Zuhr(val count: Int): QazaData(ZUHR_KEY, ZUHR_NAME, count)
    data class Asr(val count: Int): QazaData(ASR_KEY, ASR_NAME, count)
    data class Magrib(val count: Int): QazaData(MAGRIB_KEY, MAGRIB_NAME, count)
    data class Isha(val count: Int): QazaData(ISHA_KEY, ISHA_NAME, count)
    data class Utir(val count: Int): QazaData(UTIR_KEY, UTIR_NAME, count)
    object UndefinedQazaData : QazaData("", "", 0)
}