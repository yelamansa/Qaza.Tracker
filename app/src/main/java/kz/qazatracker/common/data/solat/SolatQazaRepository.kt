package kz.qazatracker.common.data.solat

import kz.qazatracker.qaza_hand_input.data.QazaData

class SolatQazaRepository(
    private val solatQazaDataSource: SolatQazaDataSource
) {

    fun getSolatQazaList(): List<QazaData>  = solatQazaDataSource.getQazaList()
}