package kz.qazatracker.common.presentation

import android.content.Context
import androidx.annotation.StringRes
import kz.qazatracker.R
import kz.qazatracker.common.data.fasting.FASTING_QAZA
import kz.qazatracker.qazainfo.presentatation.model.QazaInfoData
import kz.qazatracker.utils.LocaleHelper

class FastingQazaViewDataMapper(
        private val context: Context,
        private val localeHelper: LocaleHelper
) {

    fun map(
            remainCount: Int,
            completedCount: Int,
    ): QazaInfoData.FastingQazaViewData = QazaInfoData.FastingQazaViewData(
            key = FASTING_QAZA,
            name = getLocaledName(R.string.fasting),
            remainCount = remainCount,
            completedCount = completedCount,
            icon = R.drawable.ic_lantern
    )

    private fun getLocaledName(
            @StringRes nameResId: Int
    ): String {
        val updatedContext = localeHelper.updateContext(context)

        return updatedContext.resources.getString(nameResId)
    }

}