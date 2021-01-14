package kz.qazatracker.qaza_input.presentation

import android.content.Context
import android.content.Intent

const val IS_QAZA_CORRECTION = "is_qaza_correction"

class QazaInputRouter {

    fun createIntent(
        context: Context,
        isQazaCorrection: Boolean = false
    ) = Intent(
        context, QazaInputActivity::class.java
    ).apply {
        putExtra(IS_QAZA_CORRECTION, isQazaCorrection)
    }
}