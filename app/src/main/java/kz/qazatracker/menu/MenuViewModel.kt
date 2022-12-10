package kz.qazatracker.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.common.data.solat.SolatQazaDataSource
import kz.qazatracker.utils.LocaleHelper
import kz.qazatracker.utils.LocaleHelper.Companion.LOCALE_KZ
import kz.qazatracker.utils.LocaleHelper.Companion.LOCALE_RU

class MenuViewModel(
    private val solatQazaDataSource: SolatQazaDataSource,
    private val localeHelper: LocaleHelper
): ViewModel() {

    private val _navigationLiveData = MutableLiveData<MenuNavigation>()
    val navigationLiveData: LiveData<MenuNavigation> = _navigationLiveData

    fun onResetData() {
        solatQazaDataSource.clearQazaList()
        _navigationLiveData.value = MenuNavigation.RestartApp
    }

    fun langSelected(langCode: Int) {
        when(langCode) {
            0 -> localeHelper.setCurrentLocale(LOCALE_KZ)
            1 -> localeHelper.setCurrentLocale(LOCALE_RU)
        }
    }
}