package kz.qazatracker.calculation.presentation.model

import java.util.*

data class CalculationData(
    val birthDate: Calendar,
    val baligatStartDate: Calendar?, // Если null то пользователь не знает свой возраст балигата
    val solatStartDate: Calendar,
    val saparDays: Int,
    val hayzDays: Int,
    val bornCount: Int
)