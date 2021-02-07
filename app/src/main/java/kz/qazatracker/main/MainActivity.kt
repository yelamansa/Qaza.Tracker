package kz.qazatracker.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.qazatracker.R
import kz.qazatracker.qaza_input.presentation.QazaInputRouter
import kz.qazatracker.qaza_input.presentation.QazaInputState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var toolbar: Toolbar
    private lateinit var mainProgressBar: ProgressBar
    private lateinit var qazaProgressRecyclerView: RecyclerView
    private lateinit var qazaProgressAdapter: QazaProgressAdapter
    private lateinit var completedQazaTextView: TextView
    private lateinit var totalPrayedQazaTextView: TextView
    private lateinit var totalRemainTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initViews()
        mainViewModel.onCreate()
        observeViewModel()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val intent = QazaInputRouter().createIntent(this, QazaInputState.Reduction)
        startActivity(intent)

        return true
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.activity_main_toolbar_menu)
        toolbar.setOnMenuItemClickListener(this)
    }

    private fun initViews() {
        mainProgressBar = findViewById(R.id.main_progress_bar)
        mainProgressBar.progress = 6
        qazaProgressRecyclerView = findViewById(R.id.qaza_progress_recycler_view)
        qazaProgressRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        qazaProgressAdapter = QazaProgressAdapter()
        qazaProgressRecyclerView.adapter = qazaProgressAdapter
        completedQazaTextView = findViewById(R.id.completed_qaza_text_view)
        totalPrayedQazaTextView = findViewById(R.id.total_prayed_qaza_text_view)
        totalRemainTextView = findViewById(R.id.total_remain_text_view)
    }

    private fun observeViewModel() {
        mainViewModel.getQazaLiveData().observe(
            this,
        Observer { qazaDataList ->  qazaProgressAdapter.setList(qazaDataList)})
        mainViewModel.getQazaProgressLiveData().observe(
            this,
            Observer { qazaProgressData ->
                    completedQazaTextView.text = "Аяқталды: %.2f".format(qazaProgressData.completedPercent).plus("%")
                    totalPrayedQazaTextView.text = "Барлық оқылғандар: ${qazaProgressData.totalPreyedCount}"
                    totalRemainTextView.text = "Қалды \n ${qazaProgressData.totalRemainCount}"
            }
        )
    }
}
