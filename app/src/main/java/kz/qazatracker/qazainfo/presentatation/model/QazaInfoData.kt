package kz.qazatracker.qazainfo.presentatation.model

import androidx.annotation.DrawableRes

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
    }

    data class FastingQazaViewData(
        val name: String,
        val сount: Int,
        @DrawableRes val icon: Int
    ): QazaInfoData()

    data class QazaReadingViewData(
        val url :String
    ): QazaInfoData()
}