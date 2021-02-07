package kz.qazatracker.qaza_input.presentation

import android.content.Context
import android.content.Intent

const val QAZA_INPUT_STATE = "qaza_input_state"

class QazaInputRouter {

    fun createIntent(
        context: Context,
        qazaInputState: QazaInputState
    ) = Intent(
        context, QazaInputActivity::class.java
    ).apply {
        putExtra(QAZA_INPUT_STATE, qazaInputState)
    }
}