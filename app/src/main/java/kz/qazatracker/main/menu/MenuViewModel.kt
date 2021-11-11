package kz.qazatracker.main.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.data.QazaDataSource

class MenuViewModel(
    private val qazaDataSource: QazaDataSource
): ViewModel() {

    private val _navigationLiveData = MutableLiveData<MenuNavigation>()
    val navigationLiveData: LiveData<MenuNavigation> = _navigationLiveData

    fun onResetData() {
        qazaDataSource.clearQazaList()
        _navigationLiveData.value = MenuNavigation.RestartApp
    }
}