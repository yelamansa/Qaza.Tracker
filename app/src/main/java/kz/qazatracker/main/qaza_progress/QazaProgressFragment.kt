package kz.qazatracker.main.qaza_progress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.qazatracker.R
import kz.qazatracker.widgets.CounterWidgetCallback
import kz.qazatracker.widgets.CounterWidget
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class QazaProgressFragment : Fragment() {

    private lateinit var mainProgressBar: ProgressBar
    private lateinit var qazaProgressRecyclerView: RecyclerView
    private lateinit var qazaProgressAdapter: QazaProgressAdapter
    private lateinit var completedQazaTextView: TextView
    private lateinit var totalPrayedQazaTextView: TextView
    private lateinit var totalRemainTextView: TextView
    private lateinit var solatCounterView: CounterWidget
    private lateinit var calculatedTimeTextView: TextView
    private val qazaProgressViewModel: QazaProgressViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            Text(text = "Hello world.")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initViews(view)
//        qazaProgressViewModel.onCreate()
//        observeViewModel()
    }

    private fun initViews(view: View) {
        with(view) {
            completedQazaTextView = findViewById(R.id.completed_qaza_text_view)
            totalPrayedQazaTextView = findViewById(R.id.total_prayed_qaza_text_view)
            totalRemainTextView = findViewById(R.id.total_remain_text_view)
            mainProgressBar = findViewById(R.id.main_progress_bar)
            qazaProgressRecyclerView = findViewById(R.id.qaza_progress_recycler_view)
            solatCounterView = findViewById(R.id.solat_counter_view)
            calculatedTimeTextView = findViewById(R.id.calculated_time_text_view)
            qazaProgressRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            qazaProgressAdapter =
                QazaProgressAdapter()
            qazaProgressRecyclerView.adapter = qazaProgressAdapter
            solatCounterView.setCounterCallback(object : CounterWidgetCallback {
                override fun onCounterChanged(value: Int) {
                  calculateSolatTime(value)
                }
            })
            calculateSolatTime(solatCounterView.getCounter())
        }
    }

    private fun observeViewModel() {
        qazaProgressViewModel.getQazaLiveData().observe(
            viewLifecycleOwner,
            Observer { qazaDataList ->  qazaProgressAdapter.setList(qazaDataList)})
        qazaProgressViewModel.getQazaProgressLiveData().observe(
            viewLifecycleOwner,
            Observer { qazaProgressData ->
                completedQazaTextView.text = String.format(getString(R.string.completed_qaza_fmt), "${roundOffDecimal(qazaProgressData.completedPercent)}")
                totalPrayedQazaTextView.text = String.format(getString(R.string.total_prayed_qaza), qazaProgressData.totalPreyedCount)
                totalRemainTextView.text = String.format(getString(R.string.total_remain_qaza), qazaProgressData.totalRemainCount)
                mainProgressBar.progress = 100 - qazaProgressData.completedPercent.toInt()
            }
        )
        qazaProgressViewModel.getCalculatedRemainTime().observe(
            viewLifecycleOwner,
            Observer { calculatedRemainTime ->
                calculatedTimeTextView.text = String.format(getString(R.string.progress_assumption_info), calculatedRemainTime)
            }
        )
    }

    private fun roundOffDecimal(number: Float): Float {
        val df = DecimalFormat("#.##", DecimalFormatSymbols(Locale.ENGLISH))
        df.roundingMode = RoundingMode.CEILING

        return df.format(number).toFloat()
    }

    private fun calculateSolatTime(solatCountPerDay: Int) {
        qazaProgressViewModel.calculateRemainDate(solatCountPerDay)
    }
}