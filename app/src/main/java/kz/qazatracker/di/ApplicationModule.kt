package kz.qazatracker.di

import android.app.Activity
import kz.qazatracker.common.data.QazaUpdateDataSource
import kz.qazatracker.common.data.QazaUpdateRepository
import kz.qazatracker.qaza_auto_calculation.presentation.QazaAutoCalculationViewModel
import kz.qazatracker.data.DefaultQazaDataSource
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.menu.MenuViewModel
import kz.qazatracker.qazainfo.presentatation.QazaInfoViewModel
import kz.qazatracker.qazainfo.presentatation.SolatQazaViewDataMapper
import kz.qazatracker.qaza_hand_input.presentation.QazaHandInputState
import kz.qazatracker.qaza_hand_input.presentation.QazaHandInputViewModel
import kz.qazatracker.qazainfo.data.QazaInfoRepository
import kz.qazatracker.remoteconfig.FirebaseRemoteConfig
import kz.qazatracker.remoteconfig.RemoteConfig
import kz.qazatracker.utils.LocaleDataSource
import kz.qazatracker.utils.LocaleHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val QAZA_PREFERENCES = "qaza_preferences"

val applicationModule: Module = module {

    viewModel {
        QazaAutoCalculationViewModel(
            qazaDataSource = get()
        )
    }

    viewModel { (qazaHandInputState: QazaHandInputState)->
        QazaHandInputViewModel(
            qazaHandInputState = qazaHandInputState,
            qazaDataSource = get()
        )
    }

    viewModel {
        QazaInfoViewModel(
            qazaInfoRepository = get(),
            qazaUpdateRepository = get()
        )
    }

    viewModel {
        MenuViewModel(
            qazaDataSource = get(),
            localeHelper = get()
        )
    }

    single(named(QAZA_PREFERENCES)) {
        androidApplication().getSharedPreferences(QAZA_PREFERENCES, Activity.MODE_PRIVATE)
    }

    factory<QazaDataSource> {
        DefaultQazaDataSource(
            sharedPreferences = get(named(QAZA_PREFERENCES))
        )
    }

    factory {
        LocaleHelper(
            localeDataSource = get()
        )
    }

    factory {
        LocaleDataSource(
            sharedPreferences = get(named(QAZA_PREFERENCES))
        )
    }
    factory<RemoteConfig> {
        FirebaseRemoteConfig()
    }
    factory {
        SolatQazaViewDataMapper(
            context = androidContext(),
            localeHelper = get()
        )
    }
    factory {
        QazaInfoRepository(
            qazaDataSource = get(),
            solatQazaViewDataMapper = get()
        )
    }
    factory {
        QazaUpdateDataSource(
            sharedPreferences = get(named(QAZA_PREFERENCES))
        )
    }
    factory {
        QazaUpdateRepository(
            qazaUpdateDataSource = get()
        )
    }
}