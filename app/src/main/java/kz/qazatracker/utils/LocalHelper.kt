package kz.qazatracker.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.Locale

/**
 * Класс-помощьник для работы с локализацией приложения.
 */
open class LocaleHelper(
    private val localeDataSource: LocaleDataSource
) {

    private var currentLocale: Locale = getLocale()

    companion object {
        @JvmField
        val LOCALE_KZ = Locale("kk", "KZ")
        @JvmField
        val LOCALE_RU = Locale("ru", "RU")
    }

    @JvmOverloads
    fun updateContext(context: Context, locale: Locale = currentLocale): Context {
        var updatedContext = context
        val resources = context.resources
        val configuration = Configuration(resources.configuration)

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                configuration.setLocale(locale)
                configuration.setLocales(getLocales(locale))
                updatedContext = context.createConfigurationContext(configuration)
            }
            else -> {
                configuration.setLocale(locale)
                updatedContext = context.createConfigurationContext(configuration)
            }
        }

        return updatedContext
    }

    fun setCurrentLocale(locale: Locale) {
        Locale.setDefault(locale)
        currentLocale = locale
        localeDataSource.setLocalName(locale.language)
    }

    fun getCurrentLocale(): Locale = currentLocale

    private fun getLocale(): Locale {
        val localeName = localeDataSource.getLocaleName() ?: DEFAULT_LOCALE_NAME

        return if (localeName == LOCALE_KZ.language) {
            LOCALE_KZ
        } else {
            LOCALE_RU
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun getLocales(locale: Locale): LocaleList {
        val localeList = LocaleList(locale)
        LocaleList.setDefault(localeList)

        return localeList
    }
}