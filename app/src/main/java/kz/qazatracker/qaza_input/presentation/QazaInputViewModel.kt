package kz.qazatracker.qaza_input.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.qaza_input.data.QazaData

class QazaInputViewModel(
    private val qazaInputState: QazaInputState,
    private val qazaDataSource: QazaDataSource
): ViewModel() {

    private val qazaInputViewLiveData = MutableLiveData<QazaInputView>()

    init {
        when(qazaInputState) {
            QazaInputState.Correction -> {
                qazaInputViewLiveData.value = QazaInputView.QazaInputPreFilled(
                    qazaDataSource.getQazaList()
                )
            }
            is QazaInputState.Reduction -> {
                qazaInputViewLiveData.value = QazaInputView.QazaInputMinValues(
                    qazaDataSource.getQazaList()
                )
            }
        }
    }

    fun getQazaInputViewLiveData(): LiveData<QazaInputView> = qazaInputViewLiveData

    fun saveQaza(inputQazaDataList: List<QazaData>) {
        val actualQazaList: List<QazaData> = qazaDataSource.getQazaList()
        actualQazaList.forEachIndexed { i, element ->
            element.solatCount += inputQazaDataList[i].solatCount
        }
        qazaDataSource.saveQazaList(actualQazaList)
        qazaInputViewLiveData.value = QazaInputView.NavigationToMain
    }
}