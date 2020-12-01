package kz.qazatracker.data

import kz.qazatracker.qaza_input.data.QazaData

interface QazaDataSource {

    fun saveQaza(qazaData: QazaData)

    fun getQaza(): QazaData
}