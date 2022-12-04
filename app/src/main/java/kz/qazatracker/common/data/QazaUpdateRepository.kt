package kz.qazatracker.common.data

const val SOLAT_KEY_FORMAT = "%s_solat"

class QazaUpdateRepository(
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
}