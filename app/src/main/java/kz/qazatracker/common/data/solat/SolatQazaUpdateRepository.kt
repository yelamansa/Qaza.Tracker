package kz.qazatracker.common.data.solat

import kz.qazatracker.common.data.QazaUpdateDataSource

const val SOLAT_KEY_FORMAT = "%s_sapar"

class SolatQazaUpdateRepository(
    private val qazaUpdateDataSource: QazaUpdateDataSource
) {

    fun increaseQazaValue(
        solatKey: String,
        isSapar: Boolean
    ) {
        val properSolatKey = if (isSapar) {
            SOLAT_KEY_FORMAT.format(solatKey)
        } else {
            solatKey
        }
        qazaUpdateDataSource.increaseQazaValue(properSolatKey)
    }

    fun decreaseQazaValue(
        solatKey: String,
        isSapar: Boolean
    ) {
        val properSolatKey = if (isSapar) {
            SOLAT_KEY_FORMAT.format(solatKey)
        } else {
            solatKey
        }
        qazaUpdateDataSource.decreaseQazaValue(properSolatKey)
    }

    fun updateQazaValue(
        qazaKey: String,
        value: Int
    ) {
        qazaUpdateDataSource.updateQazaValue(qazaKey, value)
    }
}