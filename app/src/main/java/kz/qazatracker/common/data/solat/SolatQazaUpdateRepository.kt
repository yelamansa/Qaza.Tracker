package kz.qazatracker.common.data.solat

import kz.qazatracker.common.data.QazaUpdateDataSource

const val SOLAT_KEY_FORMAT = "%s_sapar"

class SolatQazaUpdateRepository(
    private val qazaUpdateDataSource: QazaUpdateDataSource
) {

    fun increaseQazaValue(
        solatKey: String,
    ) {
        qazaUpdateDataSource.increaseQazaValue(solatKey)
    }

    fun decreaseQazaValue(
        solatKey: String,
    ) {
        qazaUpdateDataSource.decreaseQazaValue(solatKey)
    }
}