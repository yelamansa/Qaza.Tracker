package kz.qazatracker

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import kz.qazatracker.widgets.DefaultCounterWidget

class QazaCalculationActivity : AppCompatActivity() {

    private lateinit var genderTabLayout: TabLayout
    private lateinit var birthDateTextView: TextView
    private lateinit var baligatDateTextView: TextView
    private lateinit var solatStartDateTextView: TextView
    private lateinit var baligatDateUnknownCheckbox: CheckBox
    private lateinit var solatStartTodayCheckBox: CheckBox
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
    }

    private fun initViews() {
        birthDateTextView = findViewById(R.id.birth_date_text_view)
        baligatDateTextView = findViewById(R.id.baligat_date_text_view)
        solatStartDateTextView = findViewById(R.id.solat_start_date_text_view)
        baligatDateUnknownCheckbox = findViewById(R.id.baligat_date_unknown_checkbox)
        solatStartTodayCheckBox = findViewById(R.id.solat_start_today_date_checkbox)
        saparDaysInputContainer = findViewById(R.id.sapar_input_container)
        hayzDaysTextView = findViewById(R.id.haiz_days_text_view)
        hayzInputContainer = findViewById(R.id.hayz_input_container)
        bornCountTextView = findViewById(R.id.born_count_text_view)
        bornCountInputContainer = findViewById(R.id.born_count_input_container)
        initGenderSwitcherView()
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

    private fun initGenderSwitcherView() {
        genderTabLayout = findViewById(R.id.gender_tab_layout)
        genderTabLayout.addTab(genderTabLayout.newTab().setText(resources.getString(R.string.male)))
        genderTabLayout.addTab(genderTabLayout.newTab().setText(resources.getString(R.string.female)))
        genderTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab == null) return

                when (tab.position) {
                    0 -> {
                        femaleViews.forEach { it.visibility = GONE }
                    }
                    1 -> {
                        femaleViews.forEach { it.visibility = VISIBLE }
                    }
                }
            }
        })
    }
}
