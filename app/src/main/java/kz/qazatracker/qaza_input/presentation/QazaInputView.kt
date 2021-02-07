package kz.qazatracker.qaza_input.presentation

import kz.qazatracker.qaza_input.data.QazaData

sealed class QazaInputView {

    data class QazaInputPreFilled(val qazaDataList: List<QazaData>): QazaInputView()
    data class QazaInputMinValues(val qazaDataList: List<QazaData>): QazaInputView()
    object NavigationToMain: QazaInputView()
}