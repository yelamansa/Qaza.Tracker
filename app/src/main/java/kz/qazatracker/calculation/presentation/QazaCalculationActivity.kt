package kz.qazatracker.calculation.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayout
import kz.qazatracker.R
import kz.qazatracker.calculation.presentation.model.BaligatAgeNotValid
import kz.qazatracker.calculation.presentation.model.CalculationData
import kz.qazatracker.calculation.presentation.model.ExceptionData
import kz.qazatracker.utils.EventObserver
import kz.qazatracker.utils.hide
import kz.qazatracker.utils.show
import kz.qazatracker.widgets.DatePickerTextView
import kz.qazatracker.widgets.DefaultCounterWidget
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

const val UNKNOWN_COUNT = -1
const val DEFAULT_BALIGAT_OLD = 12

private const val MALE_TAB_POSITION = 1

class QazaCalculationActivity : AppCompatActivity() {

    private lateinit var genderTabLayout: TabLayout
    private lateinit var birthDateTextView: DatePickerTextView
    private lateinit var baligatDateTextView: DatePickerTextView
    private lateinit var solatStartDateTextView: DatePickerTextView
    private lateinit var baligatDateUnknownCheckbox: CheckBox
    private lateinit var solatStartTodayCheckBox: CheckBox
    private lateinit var hayzDaysTextView: TextView
    private lateinit var hayzInputContainer: DefaultCounterWidget
    private lateinit var bornCountTextView: TextView
    private lateinit var bornCountInputContainer: DefaultCounterWidget
    private lateinit var calculateButton: Button
    private lateinit var femailContainer: View
    private var unknownBaligatDateDialog: AlertDialog? = null

    private lateinit var inputViews: MutableList<View>

    private val calculationViewModel: CalculationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qaza_calculation)

        initViews()
        observeViewModelLiveData()

        baligatDateUnknownCheckbox.setOnCheckedChangeListener { _, isChecked ->
            baligatDateTextView.isClickable = isChecked.not()
            if (isChecked) {
                if (unknownBaligatDateDialog == null) {
                    unknownBaligatDateDialog = createDialog(
                        message = "Егер балиғат жасқа толған уақытын білмесеңіз, автоматты түрде 12 жас есепке алынады"
                    ).show()
                } else {
                    unknownBaligatDateDialog?.show()
                }
            } else {
                unknownBaligatDateDialog?.dismiss()
            }
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
        solatStartTodayCheckBox = findViewById(R.id.solat_start_today_date_checkbox)
        hayzDaysTextView = findViewById(R.id.haiz_days_text_view)
        hayzInputContainer = findViewById(R.id.hayz_input_container)
        bornCountTextView = findViewById(R.id.born_count_text_view)
        bornCountInputContainer = findViewById(R.id.born_count_input_container)
        calculateButton = findViewById(R.id.calculate_button)
        femailContainer = findViewById(R.id.femail_container)
        initActionBar()
        initGenderSwitcherView()
        collectAllInputViews()

        birthDateTextView.setDateInMillis(getDefaultBirthDateInMillis())

        calculateButton.setOnClickListener {
            onCalculationButtonClicked()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initActionBar() {
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun observeViewModelLiveData() {
        calculationViewModel.getExceptionLiveData()
            .observe(this, EventObserver { handleExceptions(it) })
    }

    private fun handleExceptions(exceptionData: ExceptionData) {
        when (exceptionData) {
            is BaligatAgeNotValid -> {
                showMessageDialog(R.string.exception, R.string.baligat_not_valid)
            }
        }
    }

    private fun showMessageDialog(
        title: Int,
        message: Int
    ) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(R.string.ok) { _, _ -> }
        }.show()
    }

    private fun onCalculationButtonClicked() {
        val birthDate: Calendar = birthDateTextView.getCalendarDate()
        val baligatStartDate: Calendar? = if (baligatDateUnknownCheckbox.isChecked) {
            null
        } else {
            baligatDateTextView.getCalendarDate()
        }
        val solatStartDate: Calendar = solatStartDateTextView.getCalendarDate()
        val hayzDays: Int = if (genderTabLayout.selectedTabPosition == MALE_TAB_POSITION) {
            hayzInputContainer.getCounter()
        } else {
            UNKNOWN_COUNT
        }
        val bornCount: Int = if (genderTabLayout.selectedTabPosition == MALE_TAB_POSITION) {
            bornCountInputContainer.getCounter()
        } else {
            UNKNOWN_COUNT
        }

        val calculationData = CalculationData(
            birthDate,
            baligatStartDate,
            solatStartDate,
            hayzDays,
            bornCount
        )

        calculationViewModel.saveCalculationData(calculationData)
    }

    private fun collectAllInputViews() {
        inputViews = mutableListOf<View>().apply {
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
                        femailContainer.hide()
                        inputViews.forEach { it.clearFocus() }
                    }
                    1 -> {
                        femailContainer.show()
                        inputViews.forEach { it.clearFocus() }
                    }
                }
            }
        })
    }

    private fun getDefaultBirthDateInMillis(): Long {
        val defaultDateCalendar = Calendar.getInstance()
        val twelveYears = defaultDateCalendar[Calendar.YEAR] - DEFAULT_BALIGAT_OLD
        defaultDateCalendar[Calendar.YEAR] = twelveYears

        return defaultDateCalendar.timeInMillis
    }

    private fun createDialog(
        title: String? = null,
        message: String? = null
    ) = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.ok) { dialog, _ ->  dialog.dismiss()}
}
