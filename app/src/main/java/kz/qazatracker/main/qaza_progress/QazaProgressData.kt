package kz.qazatracker.main.qaza_progress

data class QazaProgressData(
    val completedPercent: Float,
    val totalPreyedCount: Int,
    val totalRemainCount: Int
)