package kz.qazatracker.di

import android.app.Activity
import kz.qazatracker.calculation.presentation.CalculationViewModel
import kz.qazatracker.data.DefaultQazaDataSource
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.main.MainViewModel
import kz.qazatracker.qaza_input.presentation.QazaInputViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val QAZA_PREFERENCES = "qaza_preferences"

val applicationModule: Module = module {

    viewModel {
        CalculationViewModel(
            qazaDataSource = get()
        )
    }

    viewModel {
        QazaInputViewModel(
            qazaDataSource = get()
        )
    }

    viewModel {
        MainViewModel(
            qazaDataSource = get()
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
}