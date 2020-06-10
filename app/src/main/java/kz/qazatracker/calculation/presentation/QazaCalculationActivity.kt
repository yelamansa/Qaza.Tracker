package kz.qazatracker.calculation.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import kz.qazatracker.R
import kz.qazatracker.calculation.presentation.model.CalculationData
import kz.qazatracker.utils.hide
import kz.qazatracker.utils.show
import kz.qazatracker.widgets.DatePickerTextView
import kz.qazatracker.widgets.DefaultCounterWidget
import org.koin.android.viewmodel.ext.android.viewModel

const val UNKNOWN_DATE = -1L
const val UNKNOWN_COUNT = -1

private const val MALE_TAB_POSITION = 1

class QazaCalculationActivity : AppCompatActivity() {

    private lateinit var genderTabLayout: TabLayout
    private lateinit var birthDateTextView: DatePickerTextView
    private lateinit var baligatDateTextView: DatePickerTextView
    private lateinit var baligatDateAttentionTextView: TextView
    private lateinit var solatStartDateTextView: DatePickerTextView
    private lateinit var baligatDateUnknownCheckbox: CheckBox
    private lateinit var solatStartTodayCheckBox: CheckBox
    private lateinit var saparDaysInputContainer: DefaultCounterWidget
    private lateinit var hayzDaysTextView: TextView
    private lateinit var hayzInputContainer: DefaultCounterWidget
    private lateinit var bornCountTextView: TextView
    private lateinit var bornCountInputContainer: DefaultCounterWidget
    private lateinit var calculateButton: Button

    private lateinit var femaleViews: MutableList<View>
    private lateinit var inputViews: MutableList<View>

    private val calculationViewModel: CalculationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qaza_calculation)

        initViews()

        baligatDateUnknownCheckbox.setOnCheckedChangeListener { _, isChecked ->
            baligatDateTextView.isClickable = isChecked.not()
            if (isChecked) baligatDateAttentionTextView.show() else baligatDateAttentionTextView.hide()
        }
        solatStartTodayCheckBox.setOnCheckedChangeListener { _, isChecked ->
            solatStartDateTextView.isClickable = isChecked.not()
        }
    }

    private fun initViews() {
        birthDateTextView = findViewById(R.id.birth_date_text_view)
        baligatDateTextView = findViewById(R.id.baligat_date_text_view)
        solatStartDateTextView = findViewById(R.id.solat_start_date_text_view)
        baligatDateUnknownCheckbox = findViewById(R.id.baligat_date_unknown_checkbox)
        baligatDateAttentionTextView = findViewById(R.id.baligat_date_unknown_attention_text_view)
        solatStartTodayCheckBox = findViewById(R.id.solat_start_today_date_checkbox)
        saparDaysInputContainer = findViewById(R.id.sapar_input_container)
        hayzDaysTextView = findViewById(R.id.haiz_days_text_view)
        hayzInputContainer = findViewById(R.id.hayz_input_container)
        bornCountTextView = findViewById(R.id.born_count_text_view)
        bornCountInputContainer = findViewById(R.id.born_count_input_container)
        calculateButton = findViewById(R.id.calculate_button)
        initGenderSwitcherView()
        collectFemaleViews()
        collectAllInputViews()

        calculateButton.setOnClickListener {
            onCalculationButtonClicked()
        }
    }

    private fun onCalculationButtonClicked() {
        val birthDate = birthDateTextView.getTimeInMillis()
        val baligatDate = if (baligatDateUnknownCheckbox.isChecked) {
            UNKNOWN_DATE
        } else {
            baligatDateTextView.getTimeInMillis()
        }
        val solatStartDate = solatStartDateTextView.getTimeInMillis()
        val saparDays = saparDaysInputContainer.getCounter()
        val hayzDays =  if (genderTabLayout.selectedTabPosition == MALE_TAB_POSITION) {
            hayzInputContainer.getCounter()
        } else {
            UNKNOWN_COUNT
        }
        val bornCount = if (genderTabLayout.selectedTabPosition == MALE_TAB_POSITION) {
            bornCountInputContainer.getCounter()
        } else {
            UNKNOWN_COUNT
        }

        val calculationData = CalculationData(
            birthDate,
            baligatDate,
            solatStartDate,
            saparDays,
            hayzDays,
            bornCount
        )

        calculationViewModel.saveCalculationData(calculationData)
    }

    private fun collectFemaleViews() {
        femaleViews = mutableListOf<View>().apply {
            add(hayzDaysTextView)
            add(hayzInputContainer)
            add(bornCountTextView)
            add(bornCountInputContainer)
        }
    }

    private fun collectAllInputViews() {
        inputViews = mutableListOf<View>().apply {
            add(saparDaysInputContainer)
            add(hayzInputContainer)
            add(bornCountInputContainer)
        }
    }

    private fun initGenderSwitcherView() {
        genderTabLayout = findViewById(R.id.gender_tab_layout)
        genderTabLayout.addTab(
            genderTabLayout.newTab().setText(
                resources.getString(
                    R.string.male
                )
            )
        )
        genderTabLayout.addTab(
            genderTabLayout.newTab().setText(
                resources.getString(
                    R.string.female
                )
            )
        )
        genderTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null) return

                when (tab.position) {
                    0 -> {
                        femaleViews.forEach { it.hide() }
                        inputViews.forEach { it.clearFocus() }
                    }
                    1 -> {
                        femaleViews.forEach { it.show() }
                        inputViews.forEach { it.clearFocus() }
                    }
                }
            }
        })
    }
}
