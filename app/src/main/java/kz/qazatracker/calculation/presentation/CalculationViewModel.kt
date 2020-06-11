package kz.qazatracker.calculation.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.calculation.presentation.model.BaligatAgeNotValid
import kz.qazatracker.calculation.presentation.model.CalculationData
import kz.qazatracker.calculation.presentation.model.ExceptionData
import kz.qazatracker.utils.Event
import java.util.*

private const val MIN_BALIGAT_DATE = 8

class CalculationViewModel: ViewModel() {

    private var isValid = true

    private val exceptionLiveDate = MutableLiveData<Event<ExceptionData>>()

    fun saveCalculationData(calculationData: CalculationData) {
        validate(calculationData)

        if (isValid) {
            calculate(calculationData)
        }
    }

    fun getExceptionLiveData(): LiveData<Event<ExceptionData>> = exceptionLiveDate

    private fun calculate(calculationData: CalculationData) {
        //Make calculation
    }

    private fun validate(calculationData: CalculationData) {
        validateBaligatAge(calculationData.birthDateInMillis, calculationData.baligatDateInMillis)
    }

    private fun validateBaligatAge(
        birthDateInMillis: Long,
        baligatDateInMillis: Long
    ) {
        val birthDateCalendar = Calendar.getInstance().apply {
            timeInMillis = birthDateInMillis
        }
        val baligatDateCalendar = Calendar.getInstance().apply {
            timeInMillis = baligatDateInMillis
        }
        val ageDifference = baligatDateCalendar.get(Calendar.YEAR) - birthDateCalendar.get(Calendar.YEAR)
        if (ageDifference < MIN_BALIGAT_DATE) {
            exceptionLiveDate.value = Event(BaligatAgeNotValid)
            isValid = false

            return
        }

        isValid = true
    }
}