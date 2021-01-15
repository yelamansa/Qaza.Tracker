package kz.qazatracker.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import kz.qazatracker.R
import kz.qazatracker.qaza_input.presentation.QazaInputRouter
import kz.qazatracker.qaza_input.presentation.QazaInputState

class MainActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {

    private lateinit var toolbar: Toolbar
    private lateinit var mainProgressBar: ProgressBar

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
        mainProgressBar = findViewById(R.id.main_progress_bar)
        mainProgressBar.progress = 6
    }
}
