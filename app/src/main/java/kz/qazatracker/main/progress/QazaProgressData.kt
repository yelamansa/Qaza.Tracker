package kz.qazatracker.main.progress

data class QazaProgressData(
    val completedPercent: Float,
    val totalPreyedCount: Int,
    val totalRemainCount: Int
)