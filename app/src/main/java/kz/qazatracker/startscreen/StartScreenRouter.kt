package kz.qazatracker.startscreen

import android.content.Context
import android.content.Intent

class StartScreenRouter {

    fun createIntent(
        context: Context
    ) = Intent(context, StartScreenActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    }
}