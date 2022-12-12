package kz.qazatracker.common.data.fasting

class FastingQazaRepository(
    private val fastingQazaDataSource: FastingQazaDataSource
) {

    fun getFastingQazaCount(): Int = fastingQazaDataSource.getFastingQazaData()
}