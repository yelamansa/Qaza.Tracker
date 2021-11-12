package kz.qazatracker.utils

import android.content.SharedPreferences
import kz.qazatracker.utils.LocaleHelper.Companion.LOCALE_RU

val DEFAULT_LOCALE_NAME = LOCALE_RU.language
private const val LOCALE_KEY = "locale_key"

class LocaleDataSource(
        private val sharedPreferences: SharedPreferences
) {

    fun getLocaleName(): String? = sharedPreferences.getString(LOCALE_KEY, DEFAULT_LOCALE_NAME)

    fun setLocalName(localeName: String) = sharedPreferences.edit()
            .putString(LOCALE_KEY, localeName)
            .apply()
}