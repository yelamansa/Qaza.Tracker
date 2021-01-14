package kz.qazatracker.qaza_input.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.qaza_input.data.QazaData

class QazaInputViewModel(
    private val qazaDataSource: QazaDataSource
): ViewModel() {

    private val qazaInputViewLiveData = MutableLiveData<QazaInputView>()

    fun onCreate(isQazaCorrection: Boolean) {
        if (isQazaCorrection) {
            qazaInputViewLiveData.value = QazaInputView.QazaInputPreFilled(
                qazaDataSource.getQaza()
            )
        }
    }

    fun getQazaInputViewLiveData(): LiveData<QazaInputView> = qazaInputViewLiveData

    fun saveQaza(qazaData: QazaData) {
        qazaDataSource.saveQaza(qazaData)
        qazaInputViewLiveData.value = QazaInputView.NavigationToMain
    }
}