package kz.qazatracker.qaza_input.presentation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class QazaInputState : Parcelable {

    @Parcelize
    object Correction: QazaInputState()

    @Parcelize
    object Start: QazaInputState()

    @Parcelize
    object Reduction: QazaInputState()
}