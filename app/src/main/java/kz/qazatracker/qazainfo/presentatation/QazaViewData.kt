package kz.qazatracker.qazainfo.presentatation

import androidx.annotation.DrawableRes

data class QazaViewData(
    val key: String,
    val name: String,
    val count: Int,
    val saparCount: Int,
    val hasSapar: Boolean,
    @DrawableRes val icon: Int
)
