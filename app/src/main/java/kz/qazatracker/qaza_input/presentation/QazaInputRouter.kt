package kz.qazatracker.qaza_input.presentation

import android.content.Context
import android.content.Intent

class QazaInputRouter {

    fun createIntent(context: Context) = Intent(
        context, QazaInputActivity::class.java
    )
}