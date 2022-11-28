package kz.qazatracker.main

import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import kz.qazatracker.R
import kz.qazatracker.main.menu.MenuFragment
import kz.qazatracker.main.qaza_progress.QazaProgressFragment
import kz.qazatracker.qaza_hand_input.presentation.QazaInputRouter
import kz.qazatracker.qaza_hand_input.presentation.QazaHandInputState
import kz.qazatracker.remoteconfig.RemoteConfig
import kz.qazatracker.utils.BaseActivity
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity() {

    private val remoteConfig: RemoteConfig by inject()
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        remoteConfig.fetchAndActivate(this)
    }

    private fun initViews() {
        replaceToProgress()
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_progress -> {
                    replaceToProgress()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_add -> {
                    val intent = QazaInputRouter().createIntent(this, QazaHandInputState.QazaEdit)
                    startActivity(intent)

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_settings -> {
                    replaceToSettings()

                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun replaceToProgress() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<QazaProgressFragment>(R.id.activity_main_fragment_container)
        }
    }

    private fun replaceToSettings() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<MenuFragment>(R.id.activity_main_fragment_container)
        }
    }
}
