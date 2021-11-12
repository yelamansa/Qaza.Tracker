package kz.qazatracker.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject

open class BaseActivity: AppCompatActivity() {

    private val localeHelper: LocaleHelper by inject()

    override fun attachBaseContext(newBase: Context) {
        val updatedContext: Context = localeHelper.updateContext(newBase)
        applyOverrideConfiguration(updatedContext.resources.configuration)

        super.attachBaseContext(updatedContext)
    }
}