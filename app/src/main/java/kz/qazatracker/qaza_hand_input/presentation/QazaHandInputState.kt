package kz.qazatracker.qaza_hand_input.presentation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class QazaHandInputState : Parcelable {

    /*Автоматты түрде қойылған қазаны коррекция жасау үшін*/
    @Parcelize
    object QazaAutoCalculateCorrection: QazaHandInputState()

    /*Қазаларды алғашқы рет енгізу үшін*/
    @Parcelize
    object Start: QazaHandInputState()

    /*Қаза намаздарды азайту үшін*/
    @Parcelize
    object QazaMinus : QazaHandInputState()

    @Parcelize
    object None: QazaHandInputState()
}