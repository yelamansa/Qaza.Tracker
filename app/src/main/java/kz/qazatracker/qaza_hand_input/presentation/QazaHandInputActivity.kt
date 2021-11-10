package kz.qazatracker.qaza_hand_input.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.qazatracker.R
import kz.qazatracker.main.MainRouter
import kz.qazatracker.qaza_hand_input.data.QazaData
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.DefinitionParameters
import org.koin.core.parameter.parametersOf

class QazaHandInputActivity : AppCompatActivity() {

    private lateinit var qazaInputRecyclerView: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var infoTextView: TextView
    private val qazaHandInputAdapter: QazaHandInputAdapter = QazaHandInputAdapter()

    private val qazaHandInputViewModel: QazaHandInputViewModel by viewModel {
        getViewModelParams()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qaza_hand_input)
        initViews()
        setUpToolbar()
        observeViewModel()
        qazaHandInputViewModel.onCreate()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initViews() {
        infoTextView = findViewById(R.id.qaza_input_info_text_view)
        qazaInputRecyclerView = findViewById(R.id.qaza_input_recycler_view)
        qazaInputRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = qazaHandInputAdapter
        }

        findViewById<Button>(R.id.save_button).setOnClickListener {
            qazaHandInputViewModel.saveQaza(
                inputQazaDataList = qazaHandInputAdapter.getList()
            )
        }
    }

    private fun setUpToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun observeViewModel() {
        qazaHandInputViewModel.getQazaDataListLiveData().observe(this, Observer { handleQazaInputView(it) })
        qazaHandInputViewModel.getQazaInputNavigationLiveData().observe(this, Observer { handleNavigation(it) })
        qazaHandInputViewModel.getTitleLiveData().observe(this, Observer { handleTitle(it) })
        qazaHandInputViewModel.getInfoLiveData().observe(this, Observer {
            infoTextView.visibility = View.VISIBLE
            infoTextView.text = it
        })
    }

    private fun handleNavigation(qazaHandInputNavigation: QazaHandInputNavigation) {
        when(qazaHandInputNavigation) {
            is QazaHandInputNavigation.MainScreen -> {
                startActivity(MainRouter().createIntent(this))
            }
        }
    }

    private fun handleQazaInputView(qazaDataList: List<QazaData>) {
        qazaHandInputAdapter.setList(qazaDataList)
    }

    private fun handleTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun getViewModelParams(): DefinitionParameters =
        parametersOf(intent.getParcelableExtra(QAZA_INPUT_STATE))
}