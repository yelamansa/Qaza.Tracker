package kz.qazatracker.qazainfo.presentatation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.common.data.fasting.FastingQazaRepository
import kz.qazatracker.common.data.solat.SolatQazaUpdateRepository
import kz.qazatracker.common.data.solat.SolatQazaRepository
import kz.qazatracker.common.presentation.FastingQazaViewDataMapper
import kz.qazatracker.qazainfo.presentatation.model.QazaInfoStateData
import kz.qazatracker.qazainfo.presentatation.model.QazaState
import kz.qazatracker.qazainfo.presentatation.model.TotalQazaState

class QazaInfoViewModel(
    private val solatQazaRepository: SolatQazaRepository,
    private val fastingQazaRepository: FastingQazaRepository,
    private val solatQazaUpdateRepository: SolatQazaUpdateRepository,
    private val solatQazaViewDataMapper: SolatQazaViewDataMapper,
    private val fastingQazaViewDataMapper: FastingQazaViewDataMapper
) : ViewModel(), QazaChangeListener {

    private val qazaInfoStateLiveData = MutableLiveData<QazaInfoStateData>()
    private val qazaChangeLiveData = MutableLiveData<QazaState>()
    private val onMenuClickLiveData = MutableLiveData<Boolean>()

    override fun onQazaDecrease(qazaKey: String) {
        solatQazaUpdateRepository.decreaseQazaValue(qazaKey)
        updateQazaInfo(qazaKey)
    }

    override fun onQazaIncrease(qazaKey: String) {
        solatQazaUpdateRepository.increaseQazaValue(qazaKey)
        updateQazaInfo(qazaKey)
    }

    fun getQazaInfoStateLiveData(): LiveData<QazaInfoStateData> = qazaInfoStateLiveData

    fun getQazaChangeLiveData(): LiveData<QazaState> = qazaChangeLiveData

    fun getMenuClickLiveData(): LiveData<Boolean> = onMenuClickLiveData

    fun onCreate() {
        updateQazaInfo()
    }

    fun onQazaChangeClick(qazaViewData: QazaState) {
        qazaChangeLiveData.value = qazaViewData
    }

    private fun updateQazaInfo(qazaKey: String? = null)  {
        var totalRemainQazaCount = 0
        var totalCompletedQazaCount = 0
        val qazaList: List<QazaState> = solatQazaRepository.getSolatQazaList().map {
            totalCompletedQazaCount += it.completedCount
            totalRemainQazaCount += it.solatCount + (it.saparSolatData?.count ?:0)

            solatQazaViewDataMapper.map(it)
        } + fastingQazaViewDataMapper.map(
                        remainCount = fastingQazaRepository.getFastingQazaCount(),
                        completedCount = fastingQazaRepository.getCompletedQazaCount()
        )
        totalCompletedQazaCount += fastingQazaRepository.getCompletedQazaCount()
        totalRemainQazaCount += fastingQazaRepository.getFastingQazaCount()

        qazaInfoStateLiveData.value = QazaInfoStateData(
                totalQazaState = TotalQazaState(
                        totalCompletedCount = totalCompletedQazaCount,
                        totalRemainCount = totalRemainQazaCount
                ),
                qazaList = qazaList
        )
        qazaList.forEach {
            if (it.key == qazaKey || it.saparQazaState?.key == qazaKey) {
                qazaChangeLiveData.value = it
            }
        }
    }

    fun onMenuClick() {
        onMenuClickLiveData.value = true
    }
}