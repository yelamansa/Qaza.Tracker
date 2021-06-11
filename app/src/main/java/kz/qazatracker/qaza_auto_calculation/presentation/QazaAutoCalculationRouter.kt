package kz.qazatracker.qaza_auto_calculation.presentation

import android.content.Context
import android.content.Intent

class QazaAutoCalculationRouter {

    fun createIntent(context: Context) = Intent(
        context, QazaAutoCalculationActivity::class.java
    )
}