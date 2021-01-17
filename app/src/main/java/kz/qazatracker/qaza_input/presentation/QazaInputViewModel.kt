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

    fun onCreate(qazaInputState: QazaInputState) {
        if (qazaInputState == QazaInputState.Correction) {
            qazaInputViewLiveData.value = QazaInputView.QazaInputPreFilled(
                qazaDataSource.getQazaList()
            )
        }
    }

    fun getQazaInputViewLiveData(): LiveData<QazaInputView> = qazaInputViewLiveData

    fun saveQaza(qazaDataList: List<QazaData>) {
        qazaDataSource.saveQazaList(qazaDataList)
        qazaInputViewLiveData.value = QazaInputView.NavigationToMain
    }
}