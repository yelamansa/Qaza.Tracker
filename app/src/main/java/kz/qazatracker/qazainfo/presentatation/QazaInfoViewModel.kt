package kz.qazatracker.qazainfo.presentatation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.common.data.fasting.FastingQazaRepository
import kz.qazatracker.common.data.solat.SolatQazaUpdateRepository
import kz.qazatracker.common.data.solat.SolatQazaRepository
import kz.qazatracker.common.presentation.FastingQazaViewDataMapper
import kz.qazatracker.qazainfo.presentatation.model.QazaInfoData

class QazaInfoViewModel(
    private val solatQazaRepository: SolatQazaRepository,
    private val fastingQazaRepository: FastingQazaRepository,
    private val solatQazaUpdateRepository: SolatQazaUpdateRepository,
    private val solatQazaViewDataMapper: SolatQazaViewDataMapper,
    private val fastingQazaViewDataMapper: FastingQazaViewDataMapper
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
        solatQazaUpdateRepository.increaseQazaValue(solatKey, isSapar)
        updateQazaInfo(solatKey)
    }

    fun onQazaValueDecrement(
        solatKey: String,
        isSapar: Boolean
    ) {
        solatQazaUpdateRepository.decreaseQazaValue(solatKey, isSapar)
        updateQazaInfo(solatKey)
    }

    fun onFastingQazaClick(qazaInfoData: QazaInfoData.FastingQazaViewData) {
// TODO:  логика клика
    }

    // TODO: Поменять, чтобы не зависеть от solatKey
    private fun updateQazaInfo(solatKey: String? = null)  {
        val solatQazaList = solatQazaRepository.getSolatQazaList()
        qazaInfoListLiveData.value = solatQazaRepository.getSolatQazaList().map { solatQazaViewDataMapper.map(it) } +
                fastingQazaViewDataMapper.map(fastingQazaRepository.getFastingQazaCount())
        if (solatKey == null) {

            return
        }
        solatQazaList.forEach {
            if (it.solatKey == solatKey) {
                qazaChangeLiveData.value = solatQazaViewDataMapper.map(it)
            }
        }
    }
}