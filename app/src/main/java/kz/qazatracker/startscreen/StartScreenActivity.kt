package kz.qazatracker.startscreen

import android.os.Bundle
import android.view.View
import android.widget.Button
import kz.qazatracker.R
import kz.qazatracker.data.QazaDataSource
import kz.qazatracker.main.MainRouter
import kz.qazatracker.qaza_auto_calculation.presentation.QazaAutoCalculationRouter
import kz.qazatracker.qaza_hand_input.presentation.QazaHandInputState
import kz.qazatracker.qaza_hand_input.presentation.QazaInputRouter
import kz.qazatracker.utils.BaseActivity
import org.koin.android.ext.android.inject

class StartScreenActivity : BaseActivity(), View.OnClickListener {

    private lateinit var calculateButton: Button
    private lateinit var qazaInputButton: Button
    private val qazaDataSource: QazaDataSource by inject()

    override fun onStart() {
        super.onStart()
        if (qazaDataSource.isQazaSaved()) {
            val intent = MainRouter().createIntent(this)
            startActivity(intent)
            overridePendingTransition(0, 0);
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
        initButtons()
    }

    private fun initButtons() {
        calculateButton = findViewById(R.id.hand_input_button)
        qazaInputButton = findViewById(R.id.calculate_qaza_button)
        calculateButton.setOnClickListener(this)
        qazaInputButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.hand_input_button -> {
                startActivity(
                    QazaInputRouter().createIntent(this, QazaHandInputState.Start)
                )
            }
            R.id.calculate_qaza_button -> {
                startActivity(
                    QazaAutoCalculationRouter().createIntent(this)
                )
            }
        }
    }
}