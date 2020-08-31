package kz.qazatracker.calculation.presentation

import android.content.Context
import android.content.Intent

class QazaCalculationRouter {

    fun createIntent(context: Context) = Intent(
        context, QazaCalculationActivity::class.java
    )
}