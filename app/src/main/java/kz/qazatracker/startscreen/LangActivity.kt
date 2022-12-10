package kz.qazatracker.startscreen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import kz.qazatracker.R
import kz.qazatracker.common.data.solat.SolatQazaDataSource
import kz.qazatracker.main.MainRouter
import kz.qazatracker.utils.BaseActivity
import kz.qazatracker.utils.LocaleDataSource
import kz.qazatracker.utils.LocaleHelper
import kz.qazatracker.utils.LocaleHelper.Companion.LOCALE_KZ
import kz.qazatracker.utils.LocaleHelper.Companion.LOCALE_RU
import org.koin.android.ext.android.inject

class LangActivity : BaseActivity() {

    private val localeHelper: LocaleHelper by inject()
    private val localDataSource: LocaleDataSource by inject()
    private val solatQazaDataSource: SolatQazaDataSource by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lang_screen)
        initButtons()
        when {
            solatQazaDataSource.isQazaSaved() -> {
                val intent = MainRouter().createIntent(this)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
            localDataSource.getLocaleName() == null -> {
                val intent = StartScreenRouter().createIntent(this)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
        }
    }

    private fun initButtons() {
        findViewById<Button>(R.id.kaz_lang_button).setOnClickListener {
            localeHelper.setCurrentLocale(LOCALE_KZ)
            startFirstScreen()
        }
        findViewById<Button>(R.id.rus_lang_button).setOnClickListener {
            localeHelper.setCurrentLocale(LOCALE_RU)
            startFirstScreen()
        }
    }

    private fun startFirstScreen() {
        val intent = Intent(this, StartScreenActivity::class.java)
        startActivity(intent)
    }
}