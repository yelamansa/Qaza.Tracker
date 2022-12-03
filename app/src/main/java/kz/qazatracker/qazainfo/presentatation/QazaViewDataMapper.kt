package kz.qazatracker.qazainfo.presentatation

import android.content.Context
import kz.qazatracker.R
import kz.qazatracker.data.*
import kz.qazatracker.qaza_hand_input.data.QazaData

class QazaViewDataMapper(
    private val context: Context
) {

    fun map(
        qazaData: QazaData
    ): QazaViewData = QazaViewData(
        name = context.getString(qazaData.solatNameResId),
        count = qazaData.solatCount,
        saparCount = qazaData.saparSolatCount,
        icon = getSolatQazaIcon(qazaData.solatKey)
    )


    private fun getSolatQazaIcon(
        qazaKey: String
    ): Int = when(qazaKey) {
        FAJR_KEY -> R.drawable.ic_fajr
        ZUHR_KEY -> R.drawable.ic_zuhr
        ASR_KEY -> R.drawable.ic_asr
        MAGRIB_KEY -> R.drawable.ic_magrib
        ISHA_KEY -> R.drawable.ic_isha
        UTIR_KEY -> R.drawable.ic_utir
        else -> R.drawable.ic_zuhr
    }
}