package kz.qazatracker.common.presentation

import android.content.Context
import androidx.annotation.StringRes
import kz.qazatracker.R
import kz.qazatracker.qazainfo.presentatation.model.QazaInfoData
import kz.qazatracker.utils.LocaleHelper

class FastingQazaViewDataMapper(
    private val context: Context,
    private val localeHelper: LocaleHelper
) {

    fun map(
        fastingQazaCount: Int
    ): QazaInfoData.FastingQazaViewData = QazaInfoData.FastingQazaViewData(
        getLocaledName(R.string.fasting),
        fastingQazaCount,
        R.drawable.ic_lantern
    )

    private fun getLocaledName(
        @StringRes nameResId: Int
    ): String {
        val updatedContext = localeHelper.updateContext(context)

        return updatedContext.resources.getString(nameResId)
    }

}