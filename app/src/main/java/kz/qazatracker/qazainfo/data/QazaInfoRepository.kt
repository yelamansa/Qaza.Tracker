package kz.qazatracker.qazainfo.data

import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.qaza_hand_input.data.QazaData
import kz.qazatracker.qazainfo.presentatation.QazaViewData
import kz.qazatracker.qazainfo.presentatation.QazaViewDataMapper

class QazaInfoRepository(
    private val qazaDataSource: QazaDataSource,
    private val qazaViewDataMapper: QazaViewDataMapper
) {

    fun getQazaInfoList(): List<QazaViewData> {
        val qazaList: List<QazaData> = qazaDataSource.getQazaList()

        return qazaList.map { qazaViewDataMapper.map(it) }
    }
}