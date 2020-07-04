package kz.qazatracker.calculation.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.calculation.presentation.model.BaligatAgeNotValid
import kz.qazatracker.calculation.presentation.model.CalculationData
import kz.qazatracker.calculation.presentation.model.ExceptionData
import kz.qazatracker.utils.Event
import java.util.*

private const val MIN_BALIGAT_OLD = 8

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

    private fun calculate(data: CalculationData) {
        //qaza namaz kunderi = namaz bastagan kun - baligatka tolgan zhas
//        val qazaDateInMillis: Long = data.solatStartDate - data.baligatDate
        Log.d("QAZA", "calculate")
    }

    private fun validate(calculationData: CalculationData) {
        validateBaligatAge(calculationData.birthDate, calculationData.baligatDate)
    }

    /**
     * Проверяем возраст если меньше 8-ми выводим ошибку, так как не достиг возраста балигата
     */
    private fun validateBaligatAge(
        birthDate: Calendar,
        baligatDate: Calendar?
    ) {
        val correctBaligatDate: Calendar = getCorrectBaligatDate(birthDate, baligatDate)

        val ageDifference: Int = correctBaligatDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
        if (ageDifference < MIN_BALIGAT_OLD) {
            exceptionLiveDate.value = Event(BaligatAgeNotValid)
            isValid = false

            return
        }

        isValid = true
    }

    /**
     * Если юзер не знает своего возраста балигата
     * вычитываем по такой логике: в день рождение добавляем дефолтовое значение балигата,
     * оно состовляет 12 лет
     */
    private fun getCorrectBaligatDate(
        birthDate: Calendar,
        baligatDate: Calendar?
    ): Calendar {
        val tempBaligatDate: Calendar = birthDate
        if (baligatDate == null) {
            val baligatYear = birthDate.get(Calendar.YEAR) + DEFAULT_BALIGAT_OLD
            tempBaligatDate.set(Calendar.YEAR, baligatYear)

            return tempBaligatDate
        }

        return baligatDate
    }
}