package kz.qazatracker.qazainfo.presentatation.model

import androidx.annotation.DrawableRes

private const val SOLAT_KEY_FORMAT = "%s_sapar"

sealed class QazaInfoData {

    data class SolatQazaViewData(
        val key: String,
        val name: String,
        val count: Int,
        val saparCount: Int,
        val hasSapar: Boolean,
        @DrawableRes val icon: Int
    ): QazaInfoData() {

        fun getTotalSolatCount(): Int = count + saparCount

        fun getSaparKey(): String = if (hasSapar) {
            SOLAT_KEY_FORMAT.format(key)
        } else {
            key
        }
    }

    data class FastingQazaViewData(
        val key: String,
        val name: String,
        val сount: Int,
        @DrawableRes val icon: Int
    ): QazaInfoData()

    data class QazaReadingViewData(
        val url :String
    ): QazaInfoData()
}