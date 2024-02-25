package kz.qazatracker.qaza_hand_input.data

data class QazaData(
        val solatKey: String,
        val solatNameResId: Int,
        var solatCount: Int,
        var saparSolatCount: Int,
        var minSolatCount: Int,
        var minSaparSolatCount: Int,
        val completedCount: Int,
        val hasSaparSolat: Boolean,
        val saparSolatData: SaparQazaData? = null,
)

data class SaparQazaData(
        val key: String,
        val count: Int,
        val minCount: Int,
)