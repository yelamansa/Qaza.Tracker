package kz.qazatracker.qaza_auto_calculation.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.qaza_auto_calculation.presentation.model.BaligatAgeNotValid
import kz.qazatracker.qaza_auto_calculation.presentation.model.CalculationData
import kz.qazatracker.qaza_auto_calculation.presentation.model.ExceptionData
import kz.qazatracker.qaza_auto_calculation.presentation.model.QalqulationNavigation
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.utils.Event
import java.util.*
import java.util.concurrent.TimeUnit

private const val MIN_BALIGAT_OLD = 8
private const val MAX_BALIGAT_OLD = 15
private const val MIN_QAZA_DAYS = 1

class QazaAutoCalculationViewModel(
    private val qazaDataSource: QazaDataSource
) : ViewModel() {

    private var isValid = true
    private val exceptionLiveData = MutableLiveData<Event<ExceptionData>>()
    private val navigationLiveData = MutableLiveData<Event<QalqulationNavigation>>()

    fun saveCalculationData(calculationData: CalculationData) {
        validate(calculationData)

        if (isValid) {
            calculate(calculationData)
        }
    }

    fun getExceptionLiveData(): LiveData<Event<ExceptionData>> = exceptionLiveData

    fun getNavigationLiveData(): LiveData<Event<QalqulationNavigation>> = navigationLiveData

    private fun calculate(data: CalculationData) {
        val baligatStartDate: Calendar =
            getCorrectBaligatDate(data.birthDate, data.baligatStartDate)
        clearCalendarHours(baligatStartDate)
        clearCalendarHours(data.solatStartDate)
        val qazaDaysInMillis: Long =
            data.solatStartDate.timeInMillis - baligatStartDate.timeInMillis
        val qazaDays: Int = TimeUnit.MILLISECONDS.toDays(qazaDaysInMillis).toInt()
        val qazaDaysIsValid: Boolean = validateQazaDays(qazaDays)
        if (qazaDaysIsValid.not()) return

        qazaDataSource.saveQazaList(
            qazaDataSource.getQazaList().map { it.also {
                if (it.hasSaparSolat) {
                    it.saparSolatCount = data.saparDays
                    val saparDaysDifference = qazaDays - data.saparDays
                    it.solatCount = if (saparDaysDifference > 0) saparDaysDifference else 0
                } else {
                   it.solatCount = qazaDays
                }
            } }
        )
        navigationLiveData.value = Event(QalqulationNavigation.QazaInput)
    }

    private fun validate(calculationData: CalculationData) {
        validateBaligatAge(calculationData.birthDate, calculationData.baligatStartDate)
    }

    private fun validateQazaDays(qazaDays: Int): Boolean {
        if (qazaDays < MIN_QAZA_DAYS) {
            exceptionLiveData.value = Event(BaligatAgeNotValid)

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

        val ageDifference: Int =
            correctBaligatDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
        if (ageDifference < MIN_BALIGAT_OLD || ageDifference > MAX_BALIGAT_OLD) {
            exceptionLiveData.value = Event(BaligatAgeNotValid)
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
        val birthDayInYear: Int = birthDate.get(Calendar.DAY_OF_YEAR)
        val baligatStartYear: Int = birthYear + DEFAULT_BALIGAT_OLD
        tempBaligatStartDate.set(Calendar.YEAR, baligatStartYear)
        tempBaligatStartDate.set(Calendar.DAY_OF_YEAR, birthDayInYear-1)

        return tempBaligatStartDate
    }

    private fun clearCalendarHours(calendar: Calendar) {
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
    }
}