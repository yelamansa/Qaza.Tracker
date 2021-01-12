package kz.qazatracker.qaza_input.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kz.qazatracker.R
import kz.qazatracker.qaza_input.data.QazaData
import kz.qazatracker.widgets.DefaultCounterWidget
import org.koin.android.viewmodel.ext.android.viewModel

class QazaInputActivity : AppCompatActivity() {

    private lateinit var fajrCounterWidget: DefaultCounterWidget
    private lateinit var zuhrCounterWidget: DefaultCounterWidget
    private lateinit var asrCounterWidget: DefaultCounterWidget
    private lateinit var magribCounterWidget: DefaultCounterWidget
    private lateinit var ishaCounterWidget: DefaultCounterWidget

    private val qazaInputViewModel: QazaInputViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qaza_input)
        initViews()
    }

    private fun initViews() {
        fajrCounterWidget = findViewById(R.id.layout_qaza_input_fajr_counter)
        zuhrCounterWidget = findViewById(R.id.layout_qaza_input_zuhr_counter)
        asrCounterWidget = findViewById(R.id.layout_qaza_input_asr_counter)
        magribCounterWidget = findViewById(R.id.layout_qaza_input_isha_counter)
//        ishaCounterWidget = findViewById(R.id.layout_qaza_input_isha_counter)

        findViewById<Button>(R.id.save_button).setOnClickListener {
            qazaInputViewModel.saveQaza(
                QazaData(
                    fajr = fajrCounterWidget.getCounter(),
                    zuhr = zuhrCounterWidget.getCounter(),
                    asr = asrCounterWidget.getCounter(),
                    magrib = magribCounterWidget.getCounter(),
                    isha = 9
//                    isha = ishaCounterWidget.getCounter()
                )
            )
        }
    }
}