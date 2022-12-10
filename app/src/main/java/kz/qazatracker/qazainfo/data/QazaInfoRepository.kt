package kz.qazatracker.qazainfo.data

import kz.qazatracker.data.SolatQazaDataSource
import kz.qazatracker.qaza_hand_input.data.QazaData
import kz.qazatracker.qazainfo.presentatation.SolatQazaViewDataMapper
import kz.qazatracker.qazainfo.presentatation.model.QazaInfoData

class QazaInfoRepository(
    private val solatQazaDataSource: SolatQazaDataSource,
    private val solatQazaViewDataMapper: SolatQazaViewDataMapper
) {

    fun getQazaInfoList(): List<QazaInfoData.SolatQazaViewData> {
        val qazaList: List<QazaData> = solatQazaDataSource.getQazaList()

        return qazaList.map { solatQazaViewDataMapper.map(it) }
    }
}