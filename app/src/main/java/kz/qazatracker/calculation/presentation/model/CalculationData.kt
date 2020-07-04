package kz.qazatracker.calculation.presentation.model

import java.util.*

data class CalculationData(
    val birthDate: Calendar,
    val baligatDate: Calendar?,
    val solatStartDate: Calendar,
    val saparDays: Int,
    val hayzDays: Int,
    val bornCount: Int
)