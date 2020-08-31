package kz.qazatracker.qaza_input.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import kz.qazatracker.qaza_input.data.QazaData

class QazaInputViewModel: ViewModel() {

    fun saveQaza(qazaData: QazaData) {
        Log.d("QQQ", "Qaza data: $qazaData")
    }
}