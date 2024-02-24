package kz.qazatracker.qazainfo.presentatation.model

import androidx.annotation.DrawableRes

private const val SOLAT_KEY_FORMAT = "%s_sapar"

sealed class QazaInfoData {

    data class SolatQazaViewData(
            val key: String,
            val name: String,
            val remainCount: Int,
            val remainSaparCount: Int,
            val hasSapar: Boolean,
            val completedCount: Int,
            @DrawableRes val icon: Int
    ): QazaInfoData() {

        fun getTotalRemainCount(): Int = remainCount + remainSaparCount

        fun getSaparKey(): String = if (hasSapar) {
            SOLAT_KEY_FORMAT.format(key)
        } else {
            key
        }
    }

    data class FastingQazaViewData(
            val key: String,
            val name: String,
            val remainCount: Int,
            val completedCount: Int,
            @DrawableRes val icon: Int
    ): QazaInfoData()
}