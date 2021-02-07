package kz.qazatracker.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import kz.qazatracker.R
import kz.qazatracker.main.progress.ProgressFragment
import kz.qazatracker.qaza_input.presentation.QazaInputRouter
import kz.qazatracker.qaza_input.presentation.QazaInputState

class MainActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {

    private lateinit var toolbar: Toolbar
    private lateinit var fragmentContainerView: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initViews()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val intent = QazaInputRouter().createIntent(this, QazaInputState.Reduction)
        startActivity(intent)

        return true
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.activity_main_toolbar_menu)
        toolbar.setOnMenuItemClickListener(this)
    }

    private fun initViews() {
        fragmentContainerView = findViewById(R.id.activity_main_fragment_container)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ProgressFragment>(R.id.activity_main_fragment_container)
        }
    }
}
