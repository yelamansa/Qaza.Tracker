package kz.qazatracker.qazainfo.presentatation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.common.data.QazaUpdateRepository
import kz.qazatracker.qazainfo.data.QazaInfoRepository

class QazaInfoViewModel(
    private val qazaInfoRepository: QazaInfoRepository,
    private val qazaUpdateRepository: QazaUpdateRepository
) : ViewModel() {

    private val qazaInfoListLiveData = MutableLiveData<List<QazaViewData>>()
    private val qazaChangeLiveData = MutableLiveData<QazaViewData>()

    fun getQazaInfoListLiveData(): LiveData<List<QazaViewData>> = qazaInfoListLiveData

    fun getQazaChangeLiveData(): LiveData<QazaViewData> = qazaChangeLiveData

    fun onCreate() {
        val qazaInfo = qazaInfoRepository.getQazaInfoList()
        qazaInfoListLiveData.value = qazaInfo
    }

    fun onQazaChangeClick(qazaViewData: QazaViewData) {
        qazaChangeLiveData.value = qazaViewData
    }

    fun onQazaValueIncrement(
        solatKey: String,
        isSapar: Boolean
    ) {
        qazaUpdateRepository.increaseQazaValue(solatKey, isSapar)
    }

    fun onQazaValueDecrement(
        solatKey: String,
        isSapar: Boolean
    ) {
        qazaUpdateRepository.decreaseQazaValue(solatKey, isSapar)
    }
}