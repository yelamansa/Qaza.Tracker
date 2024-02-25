package kz.qazatracker.qazainfo.presentatation

import android.content.Context
import androidx.annotation.StringRes
import kz.qazatracker.qaza_hand_input.data.QazaData
import kz.qazatracker.qazainfo.presentatation.model.QazaState
import kz.qazatracker.qazainfo.presentatation.model.SaparQazaState
import kz.qazatracker.utils.LocaleHelper

class SolatQazaViewDataMapper(
        private val context: Context,
        private val localeHelper: LocaleHelper
) {

    fun map(
            qazaData: QazaData
    ): QazaState = QazaState(
            key = qazaData.solatKey,
            name = getLocaledName(qazaData.solatNameResId),
            remainCount = qazaData.solatCount,
            completedCount = qazaData.completedCount,
            saparQazaState = if(qazaData.saparSolatData != null) {
                SaparQazaState(
                        key = qazaData.saparSolatData.key,
                        remainCount = qazaData.saparSolatData.count
                )
            } else null
    )

    private fun getLocaledName(
            @StringRes nameResId: Int
    ): String {
        val updatedContext = localeHelper.updateContext(context)

        return updatedContext.resources.getString(nameResId)
    }
}