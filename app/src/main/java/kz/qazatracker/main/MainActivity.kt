package kz.qazatracker.main

import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import kz.qazatracker.R
import kz.qazatracker.menu.MenuFragment
import kz.qazatracker.qazainfo.presentatation.QazaInfoFragment
import kz.qazatracker.qazainfo.presentatation.QazaInfoViewModel
import kz.qazatracker.remoteconfig.RemoteConfig
import kz.qazatracker.utils.BaseActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val qazaInfoViewModel: QazaInfoViewModel by viewModel()
    private val remoteConfig: RemoteConfig by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        remoteConfig.fetchAndActivate(this)
        replaceToQazaInfo()
        qazaInfoViewModel.getMenuClickLiveData().observe(this) {
            replaceToSettings()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    private fun replaceToQazaInfo() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<QazaInfoFragment>(R.id.activity_main_fragment_container)
            addToBackStack(null)
        }
    }

    private fun replaceToSettings() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<MenuFragment>(R.id.activity_main_fragment_container)
            addToBackStack(null)
        }
    }
}
