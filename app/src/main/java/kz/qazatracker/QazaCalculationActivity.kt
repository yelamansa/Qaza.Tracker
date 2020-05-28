package kz.qazatracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView

class QazaCalculationActivity : AppCompatActivity() {

    private lateinit var birthDateTextView: TextView
    private lateinit var baligatDateTextView: TextView
    private lateinit var solatStartDateTextView: TextView
    private lateinit var baligatDateUnknownCheckbox: CheckBox
    private lateinit var solatStartTodayCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qaza_calculation)

        birthDateTextView = findViewById(R.id.birth_date_text_view)
        baligatDateTextView = findViewById(R.id.baligat_date_text_view)
        solatStartDateTextView = findViewById(R.id.solat_start_date_text_view)
        baligatDateUnknownCheckbox = findViewById(R.id.baligat_date_unknown_checkbox)
        solatStartTodayCheckBox = findViewById(R.id.solat_start_today_date_checkbox)
        baligatDateUnknownCheckbox.setOnCheckedChangeListener { _, isChecked ->
            baligatDateTextView.isClickable = isChecked.not()
        }
        solatStartTodayCheckBox.setOnCheckedChangeListener { _, isChecked ->
            solatStartDateTextView.isClickable = isChecked.not()
        }
    }
}
