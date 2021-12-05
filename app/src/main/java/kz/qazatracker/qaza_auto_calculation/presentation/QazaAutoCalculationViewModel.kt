package kz.qazatracker.qaza_auto_calculation.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.qaza_auto_calculation.presentation.model.AutoCalculationData
import kz.qazatracker.qaza_auto_calculation.presentation.model.BaligatAgeNotValid
import kz.qazatracker.qaza_auto_calculation.presentation.model.ExceptionData
import kz.qazatracker.qaza_auto_calculation.presentation.model.QalqulationNavigation
import kz.qazatracker.utils.Event
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Years
import org.joda.time.chrono.IslamicChronology
import kotlin.math.roundToInt

const val DEFAULT_BALIGAT_OLD = 15
const val MIN_BALIGAT_OLD = 6
private const val MAX_BALIGAT_OLD = 17
private const val MIN_QAZA_DAYS = 1

class QazaAutoCalculationViewModel(
    private val qazaDataSource: QazaDataSource
) : ViewModel() {

    private var isValid = true
    private val exceptionLiveData = MutableLiveData<Event<ExceptionData>>()
    private val navigationLiveData = MutableLiveData<Event<QalqulationNavigation>>()

    fun saveCalculationData(autoCalculationData: AutoCalculationData) {
        validate(autoCalculationData)

        if (isValid) {
            calculate(autoCalculationData)
        }
    }

    fun getExceptionLiveData(): LiveData<Event<ExceptionData>> = exceptionLiveData

    fun getNavigationLiveData(): LiveData<Event<QalqulationNavigation>> = navigationLiveData

    private fun calculate(data: AutoCalculationData) {
        var qazaDays = calculateQazaDays(data)
        qazaDays = calculateForFemale(qazaDays, data)
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

    private fun calculateQazaDays(
        data: AutoCalculationData
    ): Int {
        val birthDateHijrah = data.birthDate.withChronology(IslamicChronology.getInstance())
        val baligatDateHijrah = birthDateHijrah.plusYears(data.baligatOld)
        val solatStartDateHijrah = data.solatStartDate.withChronology(IslamicChronology.getInstance())

        return Days.daysBetween(baligatDateHijrah, solatStartDateHijrah).days + 1
    }

    private fun calculateForFemale(
        qazaDays: Int,
        autoCalculationData: AutoCalculationData
    ): Int {
        var sumQazaDays = qazaDays
        if (autoCalculationData.femaleBornCount != 0) {
            val nifasDays = autoCalculationData.femaleBornCount * 40
            sumQazaDays -= nifasDays
        }
        if (autoCalculationData.femaleHayzDays != 0) {
            val monthCount = (qazaDays / 30.0).roundToInt()
            val haizDays = autoCalculationData.femaleHayzDays * monthCount
            sumQazaDays -= haizDays
        }

        return sumQazaDays
    }

    private fun validate(autoCalculationData: AutoCalculationData) {
        validateBaligatAge(autoCalculationData.baligatOld)
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
        baligatOld: Int
    ) {
        if (baligatOld < MIN_BALIGAT_OLD || baligatOld > MAX_BALIGAT_OLD) {
            exceptionLiveData.value = Event(BaligatAgeNotValid)
            isValid = false
        } else {
            isValid = true
        }
    }
}