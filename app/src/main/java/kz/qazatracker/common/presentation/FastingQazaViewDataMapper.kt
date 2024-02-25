package kz.qazatracker.common.presentation

import android.content.Context
import androidx.annotation.StringRes
import kz.qazatracker.R
import kz.qazatracker.common.data.fasting.FASTING_QAZA
import kz.qazatracker.qazainfo.presentatation.model.QazaInfoData
import kz.qazatracker.qazainfo.presentatation.model.QazaState
import kz.qazatracker.utils.LocaleHelper

class FastingQazaViewDataMapper(
        private val context: Context,
        private val localeHelper: LocaleHelper
) {

    fun map(
            remainCount: Int,
            completedCount: Int,
    ): QazaState = QazaState(
            key = FASTING_QAZA,
            name = getLocaledName(R.string.fasting),
            remainCount = remainCount,
            completedCount = completedCount,
    )

    private fun getLocaledName(
            @StringRes nameResId: Int
    ): String {
        val updatedContext = localeHelper.updateContext(context)

        return updatedContext.resources.getString(nameResId)
    }

}