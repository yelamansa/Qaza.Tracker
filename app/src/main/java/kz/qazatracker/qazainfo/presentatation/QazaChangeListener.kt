package kz.qazatracker.qazainfo.presentatation

interface QazaChangeListener {

    fun onQazaIncrease(qazaKey: String)

    fun onQazaDecrease(qazaKey: String)
}