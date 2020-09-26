package kz.qazatracker.startscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kz.qazatracker.R
import kz.qazatracker.calculation.presentation.QazaCalculationRouter
import kz.qazatracker.qaza_input.presentation.QazaInputRouter

class StartScreenActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var calculateButton: Button
    private lateinit var helpCalculateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
        initButtons()
    }

    private fun initButtons() {
        calculateButton = findViewById(R.id.calculate_button)
        helpCalculateButton = findViewById(R.id.help_calculate_button)
        calculateButton.setOnClickListener(this)
        helpCalculateButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.calculate_button -> {
                startActivity(
                    QazaInputRouter().createIntent(this)
                )
            }
            R.id.help_calculate_button -> {
                startActivity(
                    QazaCalculationRouter().createIntent(this)
                )
            }
        }
    }
}