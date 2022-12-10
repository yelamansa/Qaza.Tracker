package kz.qazatracker.qazainfo.presentatation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.R
import kz.qazatracker.common.data.QazaUpdateRepository
import kz.qazatracker.qazainfo.data.QazaInfoRepository
import kz.qazatracker.qazainfo.presentatation.model.QazaInfoData

class QazaInfoViewModel(
    private val qazaInfoRepository: QazaInfoRepository,
    private val qazaUpdateRepository: QazaUpdateRepository
) : ViewModel() {

    private val qazaInfoListLiveData = MutableLiveData<List<QazaInfoData>>()
    private val qazaChangeLiveData = MutableLiveData<QazaInfoData.SolatQazaViewData>()

    fun getQazaInfoListLiveData(): LiveData<List<QazaInfoData>> = qazaInfoListLiveData

    fun getQazaChangeLiveData(): LiveData<QazaInfoData.SolatQazaViewData> = qazaChangeLiveData

    fun onCreate() {
        updateQazaInfo()
    }

    fun onQazaChangeClick(qazaViewData: QazaInfoData.SolatQazaViewData) {
        qazaChangeLiveData.value = qazaViewData
    }

    fun onQazaValueIncrement(
        solatKey: String,
        isSapar: Boolean
    ) {
        qazaUpdateRepository.increaseQazaValue(solatKey, isSapar)
        updateQazaInfo(solatKey)
    }

    fun onQazaValueDecrement(
        solatKey: String,
        isSapar: Boolean
    ) {
        qazaUpdateRepository.decreaseQazaValue(solatKey, isSapar)
        updateQazaInfo(solatKey)
    }

    fun onFastingQazaClick(qazaInfoData: QazaInfoData.FastingQazaViewData) {
// TODO:  логика клика
    }

    // TODO: Поменять, чтобы не зависеть от solatKey
    private fun updateQazaInfo(solatKey: String? = null)  {
        val qazaInfoList = qazaInfoRepository.getQazaInfoList()
        qazaInfoListLiveData.value = qazaInfoList + QazaInfoData.FastingQazaViewData(
            "Ораза",
            34,
            R.drawable.ic_fajr
        )
        if (solatKey == null) return

        qazaInfoList.forEach {
            if (it.key == solatKey) {
                qazaChangeLiveData.value = it
            }
        }
    }
}