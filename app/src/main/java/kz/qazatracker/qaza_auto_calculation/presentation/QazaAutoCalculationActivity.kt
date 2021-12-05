package kz.qazatracker.qaza_auto_calculation.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayout
import kz.qazatracker.R
import kz.qazatracker.qaza_auto_calculation.presentation.model.AutoCalculationData
import kz.qazatracker.qaza_auto_calculation.presentation.model.BaligatAgeNotValid
import kz.qazatracker.qaza_auto_calculation.presentation.model.ExceptionData
import kz.qazatracker.qaza_auto_calculation.presentation.model.QalqulationNavigation
import kz.qazatracker.qaza_hand_input.presentation.QazaHandInputState
import kz.qazatracker.qaza_hand_input.presentation.QazaInputRouter
import kz.qazatracker.utils.BaseActivity
import kz.qazatracker.utils.EventObserver
import kz.qazatracker.utils.hide
import kz.qazatracker.utils.show
import kz.qazatracker.widgets.CounterWidget
import kz.qazatracker.widgets.DatePickerTextView
import org.joda.time.DateTime
import org.joda.time.Period
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val MALE_TAB_POSITION = 1

class QazaAutoCalculationActivity : BaseActivity() {

    private lateinit var genderTabLayout: TabLayout
    private lateinit var birthDateTextView: DatePickerTextView
    private lateinit var baligatOldEditText: EditText
    private lateinit var solatStartDateTextView: DatePickerTextView
    private lateinit var hayzDaysTextView: TextView
    private lateinit var hayzInputContainer: CounterWidget
    private lateinit var bornCountTextView: TextView
    private lateinit var bornCountInputContainer: CounterWidget
    private lateinit var saparCountInputContainer: CounterWidget
    private lateinit var calculateButton: Button
    private lateinit var femailContainer: View

    private lateinit var inputViews: MutableList<View>

    private val qazaAutoCalculationViewModel: QazaAutoCalculationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qaza_auto_calculation)

        initViews()
        observeViewModelLiveData()
    }

    private fun initViews() {
        birthDateTextView = findViewById(R.id.birth_date_text_view)
        baligatOldEditText = findViewById(R.id.baligat_date_old_edit_text)
        solatStartDateTextView = findViewById(R.id.solat_start_date_text_view)
        hayzDaysTextView = findViewById(R.id.haiz_days_text_view)
        hayzInputContainer = findViewById(R.id.hayz_input_container)
        bornCountTextView = findViewById(R.id.born_count_text_view)
        bornCountInputContainer = findViewById(R.id.born_count_input_container)
        saparCountInputContainer = findViewById(R.id.sapar_input_container)
        calculateButton = findViewById(R.id.hand_input_button)
        femailContainer = findViewById(R.id.femail_container)
        initActionBar()
        initGenderSwitcherView()
        collectAllInputViews()

        birthDateTextView.setDate(getDefaultBirthDate())

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
        qazaAutoCalculationViewModel.getExceptionLiveData()
            .observe(this, EventObserver { handleExceptions(it) })
        qazaAutoCalculationViewModel.getNavigationLiveData()
            .observe(this, EventObserver { handleNavigation(it) })
    }

    private fun handleExceptions(exceptionData: ExceptionData) {
        when (exceptionData) {
            is BaligatAgeNotValid -> {
                showMessageDialog(R.string.exception, R.string.baligat_not_valid)
            }
        }
    }

    private fun handleNavigation(navigation: QalqulationNavigation) {
        when(navigation) {
            is QalqulationNavigation.QazaInput -> {
                val intent = QazaInputRouter().createIntent(this, QazaHandInputState.QazaAutoCalculateEdit)
                startActivity(intent)
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
        val birthDate: DateTime = birthDateTextView.getDateTime()
        val solatStartDate: DateTime = solatStartDateTextView.getDateTime()
        val saparDays: Int = saparCountInputContainer.getCounter()
        val femaleHazyDays: Int = if (genderTabLayout.selectedTabPosition == MALE_TAB_POSITION) {
            hayzInputContainer.getCounter()
        } else {
            0
        }
        val femaleBornCount: Int = if (genderTabLayout.selectedTabPosition == MALE_TAB_POSITION) {
            bornCountInputContainer.getCounter()
        } else {
            0
        }
        val baligatOld = if (baligatOldEditText.text.toString().isEmpty()) {
            0
        } else {
            baligatOldEditText.text.toString().toInt()
        }
        val calculationData = AutoCalculationData(
            birthDate = birthDate,
            baligatOld = baligatOld,
            solatStartDate = solatStartDate,
            saparDays = saparDays,
            femaleHayzDays = femaleHazyDays,
            femaleBornCount = femaleBornCount
        )

        qazaAutoCalculationViewModel.saveCalculationData(calculationData)
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

    private fun getDefaultBirthDate(): DateTime = DateTime().minus(Period.years(MIN_BALIGAT_OLD))
}
