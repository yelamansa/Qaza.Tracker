package kz.qazatracker.qazainfo.presentatation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.qazainfo.data.QazaInfoRepository

class QazaInfoViewModel(
    private val qazaInfoRepository: QazaInfoRepository
) : ViewModel() {

    private val qazaInfoListLiveData = MutableLiveData<List<QazaViewData>>()
    private val qazaChangeLiveData = MutableLiveData<QazaViewData>()

    fun onCreate() {
        val qazaInfo = qazaInfoRepository.getQazaInfoList()
        qazaInfoListLiveData.value = qazaInfo
    }

    fun onQazaChangeClick(qazaViewData: QazaViewData) {
        qazaChangeLiveData.value = qazaViewData
    }

    fun getQazaInfoListLiveData(): LiveData<List<QazaViewData>> = qazaInfoListLiveData

    fun getQazaChangeLiveData(): LiveData<QazaViewData> = qazaChangeLiveData
}