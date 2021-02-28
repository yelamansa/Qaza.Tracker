package kz.qazatracker.main.progress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.qazatracker.R
import kz.qazatracker.widgets.CounterWidgetCallback
import kz.qazatracker.widgets.DefaultCounterWidget
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProgressFragment : Fragment() {

    private lateinit var mainProgressBar: ProgressBar
    private lateinit var qazaProgressRecyclerView: RecyclerView
    private lateinit var qazaProgressAdapter: QazaProgressAdapter
    private lateinit var completedQazaTextView: TextView
    private lateinit var totalPrayedQazaTextView: TextView
    private lateinit var totalRemainTextView: TextView
    private lateinit var solatCounterView: DefaultCounterWidget
    private lateinit var calculatedTimeTextView: TextView
    private val progressViewModel: ProgressViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_progress, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        progressViewModel.onCreate()
        observeViewModel()
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
        progressViewModel.getQazaLiveData().observe(
            viewLifecycleOwner,
            Observer { qazaDataList ->  qazaProgressAdapter.setList(qazaDataList)})
        progressViewModel.getQazaProgressLiveData().observe(
            viewLifecycleOwner,
            Observer { qazaProgressData ->
                completedQazaTextView.text = "Аяқталды: %.2f".format(qazaProgressData.completedPercent).plus("%")
                totalPrayedQazaTextView.text = "Барлық оқылғандар: ${qazaProgressData.totalPreyedCount}"
                totalRemainTextView.text = "Қалды\n${qazaProgressData.totalRemainCount}"
                mainProgressBar.progress = 100 - qazaProgressData.completedPercent.toInt()
            }
        )
        progressViewModel.getCalculatedRemainTime().observe(
            viewLifecycleOwner,
            Observer { calculatedRemainTime ->
                calculatedTimeTextView.text = calculatedRemainTime
            }
        )
    }

    private fun calculateSolatTime(solatCountPerDay: Int) {
        progressViewModel.calculateRemainDate(solatCountPerDay)
    }
}