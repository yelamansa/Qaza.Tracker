package kz.qazatracker.qaza_auto_calculation.presentation.model

import org.joda.time.DateTime

data class AutoCalculationData(
    val birthDate: DateTime,
    val baligatOld: Int,
    val solatStartDate: DateTime,
    val saparDays: Int,
    val femaleHayzDays: Int,
    val femaleBornCount: Int
)