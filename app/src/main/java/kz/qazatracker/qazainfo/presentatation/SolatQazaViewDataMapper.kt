package kz.qazatracker.qazainfo.presentatation

import android.content.Context
import androidx.annotation.StringRes
import kz.qazatracker.R
import kz.qazatracker.common.data.solat.*
import kz.qazatracker.qaza_hand_input.data.QazaData
import kz.qazatracker.qazainfo.presentatation.model.QazaInfoData
import kz.qazatracker.utils.LocaleHelper

class SolatQazaViewDataMapper(
    private val context: Context,
    private val localeHelper: LocaleHelper
) {

    fun map(
        qazaData: QazaData
    ): QazaInfoData.SolatQazaViewData = QazaInfoData.SolatQazaViewData(
        key = qazaData.solatKey,
        name = getLocaledName(qazaData.solatNameResId),
        count = qazaData.solatCount,
        saparCount = qazaData.saparSolatCount,
        icon = getSolatQazaIcon(qazaData.solatKey),
        hasSapar = qazaData.hasSaparSolat
    )

    private fun getSolatQazaIcon(
        qazaKey: String
    ): Int = when (qazaKey) {
        FAJR_KEY -> R.drawable.ic_fajr
        ZUHR_KEY -> R.drawable.ic_zuhr
        ASR_KEY -> R.drawable.ic_asr
        MAGRIB_KEY -> R.drawable.ic_magrib
        ISHA_KEY -> R.drawable.ic_isha
        UTIR_KEY -> R.drawable.ic_utir
        else -> R.drawable.ic_zuhr
    }

    private fun getLocaledName(
        @StringRes nameResId: Int
    ): String {
        val updatedContext = localeHelper.updateContext(context)

        return updatedContext.resources.getString(nameResId)
    }
}