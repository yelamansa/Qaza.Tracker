package kz.qazatracker.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.bottomnavigation.BottomNavigationView
import kz.qazatracker.R
import kz.qazatracker.main.qaza_progress.QazaProgressFragment
import kz.qazatracker.main.settings.SettingsFragment
import kz.qazatracker.qaza_hand_input.presentation.QazaInputRouter
import kz.qazatracker.qaza_hand_input.presentation.QazaHandInputState

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initViews()
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
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
            replace<SettingsFragment>(R.id.activity_main_fragment_container)
        }
    }
}
