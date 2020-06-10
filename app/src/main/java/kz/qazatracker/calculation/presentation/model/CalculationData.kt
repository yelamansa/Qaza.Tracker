package kz.qazatracker.calculation.presentation.model

data class CalculationData(
    private val birthDate: Long,
    private val baligatDate: Long,
    private val solatStartDate: Long,
    private val saparDays: Int,
    private val hayzDays: Int,
    private val bornCount: Int
)