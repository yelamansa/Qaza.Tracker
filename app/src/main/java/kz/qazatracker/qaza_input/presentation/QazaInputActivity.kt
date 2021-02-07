package kz.qazatracker.qaza_input.presentation

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import kz.qazatracker.R
import kz.qazatracker.main.MainRouter
import kz.qazatracker.qaza_input.data.QazaData
import kz.qazatracker.widgets.DefaultCounterWidget
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.DefinitionParameters
import org.koin.core.parameter.parametersOf

class QazaInputActivity : AppCompatActivity() {

    private lateinit var fajrCounterWidget: DefaultCounterWidget
    private lateinit var zuhrCounterWidget: DefaultCounterWidget
    private lateinit var asrCounterWidget: DefaultCounterWidget
    private lateinit var magribCounterWidget: DefaultCounterWidget
    private lateinit var ishaCounterWidget: DefaultCounterWidget
    private lateinit var utirCounterWidget: DefaultCounterWidget

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

    override fun onDestroy() {
        super.onDestroy()
        unObserveViewModel()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initViews() {
        fajrCounterWidget = findViewById(R.id.layout_qaza_input_fajr_counter)
        zuhrCounterWidget = findViewById(R.id.layout_qaza_input_zuhr_counter)
        asrCounterWidget = findViewById(R.id.layout_qaza_input_asr_counter)
        magribCounterWidget = findViewById(R.id.layout_qaza_input_magrib_counter)
        ishaCounterWidget = findViewById(R.id.layout_qaza_input_isha_counter)
        utirCounterWidget = findViewById(R.id.layout_qaza_input_utir_counter)

        findViewById<Button>(R.id.save_button).setOnClickListener {
            qazaInputViewModel.saveQaza(
                listOf(
                    QazaData.Fajr(fajrCounterWidget.getCounter()),
                    QazaData.Zuhr(zuhrCounterWidget.getCounter()),
                    QazaData.Asr(asrCounterWidget.getCounter()),
                    QazaData.Magrib(magribCounterWidget.getCounter()),
                    QazaData.Isha(ishaCounterWidget.getCounter()),
                    QazaData.Utir(utirCounterWidget.getCounter())
                )
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
        qazaInputViewModel.getQazaInputViewLiveData().observe(
            this,
            Observer { qazaInputView -> handleQazaInputView(qazaInputView) }
        )
    }

    private fun unObserveViewModel() {
        qazaInputViewModel.getQazaInputViewLiveData().removeObservers(this)
    }

    private fun handleQazaInputView(qazaInputView: QazaInputView) {
        when (qazaInputView) {
            is QazaInputView.NavigationToMain -> {
                startActivity(MainRouter().createIntent(this))
            }
            is QazaInputView.QazaInputPreFilled -> {
                qazaInputView.qazaDataList.forEach { qazaData ->
                    when (qazaData) {
                        is QazaData.Fajr -> fajrCounterWidget.setCounter(qazaData.solatCount)
                        is QazaData.Zuhr -> zuhrCounterWidget.setCounter(qazaData.solatCount)
                        is QazaData.Asr -> asrCounterWidget.setCounter(qazaData.solatCount)
                        is QazaData.Magrib -> magribCounterWidget.setCounter(qazaData.solatCount)
                        is QazaData.Isha -> ishaCounterWidget.setCounter(qazaData.solatCount)
                        is QazaData.Utir -> utirCounterWidget.setCounter(qazaData.solatCount)
                    }
                }
            }
            is QazaInputView.QazaInputMinValues -> {
                qazaInputView.qazaDataList.forEach { qazaData ->
                    when (qazaData) {
                        is QazaData.Fajr -> fajrCounterWidget.setMinCounterValue(-qazaData.solatCount)
                        is QazaData.Zuhr -> zuhrCounterWidget.setMinCounterValue(-qazaData.solatCount)
                        is QazaData.Asr -> asrCounterWidget.setMinCounterValue(-qazaData.solatCount)
                        is QazaData.Magrib -> magribCounterWidget.setMinCounterValue(-qazaData.solatCount)
                        is QazaData.Isha -> ishaCounterWidget.setMinCounterValue(-qazaData.solatCount)
                        is QazaData.Utir -> utirCounterWidget.setMinCounterValue(-qazaData.solatCount)
                    }
                }
            }
        }
    }

    private fun getViewModelParams(): DefinitionParameters =
        parametersOf(intent.getParcelableExtra(QAZA_INPUT_STATE))
}