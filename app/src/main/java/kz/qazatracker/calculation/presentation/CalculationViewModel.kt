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
import java.util.concurrent.TimeUnit

private const val MIN_BALIGAT_OLD = 8
private const val MAX_BALIGAT_OLD = 15
private const val MIN_QAZA_DAYS = 1

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
        val baligatStartDate: Calendar = getCorrectBaligatDate(data.birthDate, data.baligatStartDate)
        clearCalendarHours(baligatStartDate)
        clearCalendarHours(data.solatStartDate)
        val qazaDaysInMillis: Long = data.solatStartDate.timeInMillis - baligatStartDate.timeInMillis
        val qazaDays: Long = TimeUnit.MILLISECONDS.toDays(qazaDaysInMillis)
        val qazaDaysIsValid: Boolean = validateQazaDays(qazaDays)

        if (qazaDaysIsValid.not()) return

        Log.d("QQQ", "Qaza days is: $qazaDays")
    }

    private fun validate(calculationData: CalculationData) {
        validateBaligatAge(calculationData.birthDate, calculationData.baligatStartDate)
    }

    private fun validateQazaDays(qazaDays: Long): Boolean {
        if (qazaDays < MIN_QAZA_DAYS) {
            exceptionLiveDate.value = Event(BaligatAgeNotValid)

            return false
        }

        return true
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
        if (ageDifference < MIN_BALIGAT_OLD || ageDifference > MAX_BALIGAT_OLD) {
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
        baligatStartDate: Calendar?
    ): Calendar {
        val tempBaligatStartDate: Calendar = birthDate.clone() as Calendar
        if (baligatStartDate != null) return baligatStartDate

        val birthYear: Int = birthDate.get(Calendar.YEAR)
        val baligatStartYear:Int = birthYear + DEFAULT_BALIGAT_OLD
        tempBaligatStartDate.set(Calendar.YEAR, baligatStartYear)

        return tempBaligatStartDate
    }

    private fun clearCalendarHours(calendar: Calendar) {
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MINUTE, 0)
    }
}