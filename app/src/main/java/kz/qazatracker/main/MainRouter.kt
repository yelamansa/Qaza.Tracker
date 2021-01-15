package kz.qazatracker.main

import android.content.Context
import android.content.Intent

class MainRouter {

    fun createIntent(context: Context): Intent = Intent(
        context, MainActivity::class.java
    ).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    }
}