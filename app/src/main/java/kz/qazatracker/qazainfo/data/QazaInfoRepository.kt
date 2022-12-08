package kz.qazatracker.qazainfo.data

import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.qaza_hand_input.data.QazaData
import kz.qazatracker.qazainfo.presentatation.SolatQazaViewDataMapper
import kz.qazatracker.qazainfo.presentatation.model.QazaInfoData

class QazaInfoRepository(
    private val qazaDataSource: QazaDataSource,
    private val solatQazaViewDataMapper: SolatQazaViewDataMapper
) {

    fun getQazaInfoList(): List<QazaInfoData.SolatQazaViewData> {
        val qazaList: List<QazaData> = qazaDataSource.getQazaList()

        return qazaList.map { solatQazaViewDataMapper.map(it) }
    }
}