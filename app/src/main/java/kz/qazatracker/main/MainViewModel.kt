package kz.qazatracker.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.qaza_input.data.QazaData

class MainViewModel(
    private val qazaDataSource: QazaDataSource
) : ViewModel() {

    private val qazaLiveData = MutableLiveData<List<QazaData>>()

    fun onCreate() {
        val qazaDataList = qazaDataSource.getQazaList()
        qazaLiveData.value = qazaDataList
    }

    fun getQazaLiveData() = qazaLiveData
}