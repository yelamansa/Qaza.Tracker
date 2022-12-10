package kz.qazatracker.common.data

import kz.qazatracker.qaza_hand_input.data.QazaData

interface SolatQazaDataSource {

    fun saveQazaList(qazaDataList: List<QazaData>)

    fun getQazaList(): List<QazaData>

    fun clearQazaList()

    fun getTotalCompletedQazaPercent(): Float

    fun getTotalPrayedCount(): Int

    fun getTotalPrayedCount(solatKey: String): Int

    fun saveTotalPreyedCount(count: Int)

    fun saveTotalPrayedCount(solatKey: String, count: Int)

    fun getTotalRemainCount(): Int

    fun isQazaSaved(): Boolean
}