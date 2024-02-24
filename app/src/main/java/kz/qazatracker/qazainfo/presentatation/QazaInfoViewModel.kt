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
) : ViewModel(), QazaChangeListener {

    private val qazaInfoListLiveData = MutableLiveData<List<QazaInfoData>>()
    private val qazaChangeLiveData = MutableLiveData<QazaInfoData>()
    private val onMenuClickLiveData = MutableLiveData<Boolean>()

    override fun onQazaDecrease(qazaKey: String) {
        solatQazaUpdateRepository.decreaseQazaValue(qazaKey)
        updateQazaInfo(qazaKey)
    }

    override fun onQazaIncrease(qazaKey: String) {
        solatQazaUpdateRepository.increaseQazaValue(qazaKey)
        updateQazaInfo(qazaKey)
    }

    fun getQazaInfoListLiveData(): LiveData<List<QazaInfoData>> = qazaInfoListLiveData

    fun getQazaChangeLiveData(): LiveData<QazaInfoData> = qazaChangeLiveData

    fun getMenuClickLiveData(): LiveData<Boolean> = onMenuClickLiveData

    fun onCreate() {
        updateQazaInfo()
    }

    fun onQazaChangeClick(qazaViewData: QazaInfoData) {
        qazaChangeLiveData.value = qazaViewData
    }

    private fun updateQazaInfo(qazaKey: String? = null)  {
        val qazaList: List<QazaInfoData> = solatQazaRepository.getSolatQazaList().map { solatQazaViewDataMapper.map(it) } +
                fastingQazaViewDataMapper.map(
                        remainCount = fastingQazaRepository.getFastingQazaCount(),
                        completedCount = fastingQazaRepository.getCompletedQazaCount()
                )
        qazaInfoListLiveData.value = qazaList
        if (qazaKey == null) {

            return
        }
        qazaList.forEach {
            when(it) {
                is QazaInfoData.FastingQazaViewData -> {
                    if (it.key == qazaKey) {
                        qazaChangeLiveData.value = it
                    }
                }
                is QazaInfoData.SolatQazaViewData -> {
                    if (it.key == qazaKey || it.getSaparKey() == qazaKey) {
                        qazaChangeLiveData.value = it
                    }
                }
            }
        }
    }

    fun onMenuClick() {
        onMenuClickLiveData.value = true
    }
}