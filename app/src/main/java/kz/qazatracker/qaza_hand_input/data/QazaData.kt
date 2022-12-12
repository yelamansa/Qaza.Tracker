package kz.qazatracker.qaza_hand_input.data

data class QazaData(
    val solatKey: String,
    val solatNameResId: Int,
    var solatCount: Int,
    var saparSolatCount: Int,
    var minSolatCount: Int,
    var minSaparSolatCount: Int,
    val totalPrayedCount: Int,
    val hasSaparSolat: Boolean
)