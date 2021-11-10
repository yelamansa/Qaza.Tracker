package kz.qazatracker.qaza_hand_input.presentation

import android.content.Context
import android.content.Intent

const val QAZA_INPUT_STATE = "qaza_input_state"

class QazaInputRouter {

    fun createIntent(
        context: Context,
        qazaHandInputState: QazaHandInputState
    ) = Intent(
        context, QazaHandInputActivity::class.java
    ).apply {
        putExtra(QAZA_INPUT_STATE, qazaHandInputState)
    }
}