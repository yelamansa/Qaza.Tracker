package kz.qazatracker.startscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kz.qazatracker.R
import kz.qazatracker.calculation.presentation.QazaCalculationRouter

class StartScreenActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var calculateButton: Button
    private lateinit var helpCalculateButton: Button
    private lateinit var restoreButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
        initButtons()
    }

    private fun initButtons() {
        calculateButton = findViewById(R.id.calculate_button)
        helpCalculateButton = findViewById(R.id.help_calculate_button)
        restoreButton = findViewById(R.id.restore_button)
        calculateButton.setOnClickListener(this)
        helpCalculateButton.setOnClickListener(this)
        restoreButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.calculate_button -> {
                //Переход в экран ввода дней каза
                Toast.makeText(this, "Переход в экран ввода дней каза", Toast.LENGTH_SHORT).show()
            }
            R.id.help_calculate_button -> {
                startActivity(
                    QazaCalculationRouter().createIntent(this)
                )
            }
            R.id.restore_button -> {
                //Переход в авторизацию
                Toast.makeText(this, "Переход в авторизацию", Toast.LENGTH_SHORT).show()
            }
        }
    }
}