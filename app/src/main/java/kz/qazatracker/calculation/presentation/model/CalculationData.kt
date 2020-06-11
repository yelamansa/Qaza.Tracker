package kz.qazatracker.calculation.presentation.model

data class CalculationData(
    val birthDateInMillis: Long,
    val baligatDateInMillis: Long,
    val solatStartDateInMillis: Long,
    val saparDays: Int,
    val hayzDays: Int,
    val bornCount: Int
)