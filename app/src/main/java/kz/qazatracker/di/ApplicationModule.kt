package kz.qazatracker.di

import kz.qazatracker.calculation.presentation.CalculationViewModel
import kz.qazatracker.qaza_input.presentation.QazaInputViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val applicationModule: Module = module {

    viewModel {
        CalculationViewModel()
    }

    viewModel {
        QazaInputViewModel()
    }
}