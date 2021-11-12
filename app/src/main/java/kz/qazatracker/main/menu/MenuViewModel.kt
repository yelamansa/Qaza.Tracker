package kz.qazatracker.main.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.utils.LocaleHelper2
import kz.qazatracker.utils.LocaleHelper2.Companion.LOCALE_KZ
import kz.qazatracker.utils.LocaleHelper2.Companion.LOCALE_RU

class MenuViewModel(
    private val qazaDataSource: QazaDataSource,
    private val localeHelper: LocaleHelper2
): ViewModel() {

    private val _navigationLiveData = MutableLiveData<MenuNavigation>()
    val navigationLiveData: LiveData<MenuNavigation> = _navigationLiveData

    fun onResetData() {
        qazaDataSource.clearQazaList()
        _navigationLiveData.value = MenuNavigation.RestartApp
    }

    fun langSelected(langCode: Int) {
        when(langCode) {
            0 -> localeHelper.setCurrentLocale(LOCALE_KZ)
            1 -> localeHelper.setCurrentLocale(LOCALE_RU)
        }
    }
}