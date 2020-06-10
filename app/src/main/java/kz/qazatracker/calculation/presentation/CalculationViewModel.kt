package kz.qazatracker.calculation.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import kz.qazatracker.calculation.presentation.model.CalculationData

class CalculationViewModel: ViewModel() {

    fun saveCalculationData(calculationData: CalculationData) {
        Log.d("form", calculationData.toString())
    }
}