package kz.qazatracker.qaza_input.data

data class QazaData(
    val solatKey: String,
    val solatName: String,
    var solatCount: Int,
    var saparSolatCount: Int,
    var minSolatCount: Int,
    var minSaparSolatCount: Int,
    val totalPrayedCount: Int,
    val hasSaparSolat: Boolean
) {

    fun getTotalCompletedQazaPercent(): Float{
        val totalCount = totalPrayedCount + solatCount + saparSolatCount

        if (totalCount == 0) return 0f

        return totalPrayedCount.toFloat() * 100 / totalCount
    }
}