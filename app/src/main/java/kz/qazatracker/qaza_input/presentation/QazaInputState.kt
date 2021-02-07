package kz.qazatracker.qaza_input.presentation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class QazaInputState : Parcelable {

    /*Автоматты түрде қойылған қазаны коррекция жасау үшін*/
    @Parcelize
    object Correction: QazaInputState()

    /*Қазаларды алғашқы рет енгізу үшін*/
    @Parcelize
    object Start: QazaInputState()

    /*Қаза намаздарды азайту үшін*/
    @Parcelize
    object Reduction : QazaInputState()

    @Parcelize
    object None: QazaInputState()
}