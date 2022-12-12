package kz.qazatracker.common.data.fasting

import kz.qazatracker.common.data.QazaUpdateDataSource

class FastingQazaUpdateRepository(
    private val qazaUpdateDataSource: QazaUpdateDataSource
) {

    fun increaseQazaValue() {
        qazaUpdateDataSource.increaseQazaValue(FASTING_QAZA)
    }

    fun decreaseQazaValue() {
        qazaUpdateDataSource.decreaseQazaValue(FASTING_QAZA)
    }
}