package kz.qazatracker.qaza_input.presentation

import kz.qazatracker.qaza_input.data.QazaData

sealed class QazaInputView {

    data class QazaInputPreFilled(val qazaData: QazaData): QazaInputView()
    object NavigationToMain: QazaInputView()
}