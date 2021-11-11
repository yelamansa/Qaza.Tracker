package kz.qazatracker.qaza_auto_calculation.presentation.model

import org.joda.time.DateTime

data class AutoCalculationData(
    val birthDate: DateTime,
    val baligatStartDate: DateTime?, // Если null то пользователь не знает свой возраст балигата
    val solatStartDate: DateTime,
    val saparDays: Int,
    val femaleHayzDays: Int,
    val femaleBornCount: Int
)