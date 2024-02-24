package kz.qazatracker.common.data.solat

import kz.qazatracker.qaza_hand_input.data.QazaData

interface SolatQazaDataSource {

    fun saveQazaList(qazaDataList: List<QazaData>)

    fun getQazaList(): List<QazaData>

    fun clearQazaList()

    fun getTotalCompletedQazaPercent(): Float

    fun getTotalPrayedCount(): Int

    fun getPrayedCount(solatKey: String): Int

    fun addAndSavePrayedCount(
            solatKey: String,
            count: Int
    )

    fun getTotalRemainCount(): Int

    fun isQazaSaved(): Boolean
}