package kz.qazatracker.qaza_auto_calculation.presentation.model

import java.util.*

data class AutoCalculationData(
    val birthDate: Calendar,
    val baligatStartDate: Calendar?, // Если null то пользователь не знает свой возраст балигата
    val solatStartDate: Calendar,
    val saparDays: Int,
    val femaleHayzDays: Int,
    val femaleBornCount: Int
)