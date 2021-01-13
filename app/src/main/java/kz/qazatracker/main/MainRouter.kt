package kz.qazatracker.main

import android.content.Context
import android.content.Intent

class MainRouter {

    fun createIntent(context: Context): Intent = Intent(
        context, MainActivity::class.java
    )
}