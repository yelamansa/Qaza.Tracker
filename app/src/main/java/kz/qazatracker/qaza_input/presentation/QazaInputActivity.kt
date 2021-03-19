package kz.qazatracker.qaza_input.presentation

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.qazatracker.R
import kz.qazatracker.main.MainRouter
import kz.qazatracker.qaza_input.data.QazaData
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.DefinitionParameters
import org.koin.core.parameter.parametersOf

class QazaInputActivity : AppCompatActivity() {

    private lateinit var qazaInputRecyclerView: RecyclerView
    private val qazaInputAdapter: QazaInputAdapter = QazaInputAdapter()

    private val qazaInputViewModel: QazaInputViewModel by viewModel {
        getViewModelParams()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qaza_input)
        initViews()
        setUpToolbar()
        observeViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initViews() {
        qazaInputRecyclerView = findViewById(R.id.qaza_input_recycler_view)
        qazaInputRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = qazaInputAdapter
        }

        findViewById<Button>(R.id.save_button).setOnClickListener {
            qazaInputViewModel.saveQaza(
                inputQazaDataList = qazaInputAdapter.getList()
            )
        }
    }

    private fun setUpToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    private fun observeViewModel() {
        qazaInputViewModel.getQazaDataListLiveData().observe(this, Observer { handleQazaInputView(it) })
        qazaInputViewModel.getQazaInputNavigationLiveData().observe(this, Observer { handleNavigation(it) })
    }

    private fun handleNavigation(qazaInputNavigation: QazaInputNavigation) {
        when(qazaInputNavigation) {
            is QazaInputNavigation.MainScreen -> {
                startActivity(MainRouter().createIntent(this))
            }
        }
    }

    private fun handleQazaInputView(qazaDataList: List<QazaData>) {
        qazaInputAdapter.setList(qazaDataList)
    }

    private fun getViewModelParams(): DefinitionParameters =
        parametersOf(intent.getParcelableExtra(QAZA_INPUT_STATE))
}