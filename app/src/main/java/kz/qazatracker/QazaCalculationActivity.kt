package kz.qazatracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import kz.qazatracker.widgets.DefaultCounterWidget

class QazaCalculationActivity : AppCompatActivity() {

    private lateinit var birthDateTextView: TextView
    private lateinit var baligatDateTextView: TextView
    private lateinit var solatStartDateTextView: TextView
    private lateinit var baligatDateUnknownCheckbox: CheckBox
    private lateinit var solatStartTodayCheckBox: CheckBox
    private lateinit var genderRadioButton: RadioButton
    private lateinit var saparDaysInputContainer: DefaultCounterWidget
    private lateinit var hayzDaysTextView: TextView
    private lateinit var hayzInputContainer: DefaultCounterWidget
    private lateinit var bornCountTextView: TextView
    private lateinit var bornCountInputContainer: DefaultCounterWidget

    private lateinit var femaleViews: MutableList<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qaza_calculation)

        initViews()

        baligatDateUnknownCheckbox.setOnCheckedChangeListener { _, isChecked ->
            baligatDateTextView.isClickable = isChecked.not()
        }
        solatStartTodayCheckBox.setOnCheckedChangeListener { _, isChecked ->
            solatStartDateTextView.isClickable = isChecked.not()
        }
        handleGenderChange()
    }

    private fun initViews() {
        birthDateTextView = findViewById(R.id.birth_date_text_view)
        baligatDateTextView = findViewById(R.id.baligat_date_text_view)
        solatStartDateTextView = findViewById(R.id.solat_start_date_text_view)
        baligatDateUnknownCheckbox = findViewById(R.id.baligat_date_unknown_checkbox)
        solatStartTodayCheckBox = findViewById(R.id.solat_start_today_date_checkbox)
        genderRadioButton = findViewById(R.id.male_radio_button)
        saparDaysInputContainer = findViewById(R.id.sapar_input_container)
        hayzDaysTextView = findViewById(R.id.haiz_days_text_view)
        hayzInputContainer = findViewById(R.id.hayz_input_container)
        bornCountTextView = findViewById(R.id.born_count_text_view)
        bornCountInputContainer = findViewById(R.id.born_count_input_container)

        collectFemaleViews()
    }

    private fun collectFemaleViews() {
        femaleViews = mutableListOf<View>().apply {
            add(hayzDaysTextView)
            add(hayzInputContainer)
            add(bornCountTextView)
            add(bornCountInputContainer)
        }
    }

    private fun handleGenderChange() {
        genderRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                femaleViews.forEach { it.visibility = GONE }
            } else {
                femaleViews.forEach { it.visibility = VISIBLE }
            }
        }
    }
}
