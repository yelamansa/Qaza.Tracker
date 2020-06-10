package kz.qazatracker.calculation.presentation.model

data class CalculationData(
    val birthDate: Long,
    val baligatDate: Long,
    val solatStartDate: Long,
    val saparDays: Int,
    val hayzDays: Int,
    val bornCount: Int
)