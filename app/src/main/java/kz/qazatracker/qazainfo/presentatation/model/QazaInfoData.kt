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
    ): QazaInfoData()

    data class FastingQazaViewData(
        val qazaCount: Int
    ): QazaInfoData()

    data class QazaReadingViewData(
        val url :String
    ): QazaInfoData()
}