package kz.qazatracker.data

import kz.qazatracker.qaza_input.data.QazaData

interface QazaDataSource {

    fun saveQazaList(qazaDataList: List<QazaData>)

    fun getQazaList(): List<QazaData>

    fun getQaza(solatKey: String): QazaData

    fun getCompletedQazaPercent(): Float

    fun getTotalPrayedCount(): Int

    fun saveTotalPreyedCount(count: Int)

    fun getTotalRemainCount(): Int
}