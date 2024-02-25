package kz.qazatracker.qazainfo.presentatation.model

data class QazaInfoStateData(
        val totalQazaState: TotalQazaState,
        val qazaList: List<QazaState>
)

data class TotalQazaState(
        val totalCompletedCount: Int,
        val totalRemainCount: Int,
)

data class QazaState(
        val key: String,
        val name: String,
        val remainCount: Int,
        val completedCount: Int,
        val saparQazaState: SaparQazaState? = null,
) {
    fun getTotalRemainCount(): Int = remainCount + (saparQazaState?.remainCount ?: 0)
}

data class SaparQazaState(
        val key: String,
        val remainCount: Int,
)