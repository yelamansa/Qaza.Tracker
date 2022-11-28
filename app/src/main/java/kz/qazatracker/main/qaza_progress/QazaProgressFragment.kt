package kz.qazatracker.main.qaza_progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.qazatracker.R
import kz.qazatracker.widgets.CounterWidget
import kz.qazatracker.widgets.CounterWidgetCallback
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
            QazaInfo()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initViews(view)
//        qazaProgressViewModel.onCreate()
//        observeViewModel()
    }

    private data class QazaViewData(
        val name: String,
        val count: Int,
        val saparCount: Int,
        @DrawableRes val icon: Int,
        val iconBgColor: String
    )

    @Preview
    @Composable
    private fun QazaInfo() {
        val qazaInfoList = listOf(
            QazaViewData("Таң", 43446, 64327, R.drawable.ic_fajr, "#C6D5F2"),
            QazaViewData("Бесін", 2323, 43453, R.drawable.ic_zuhr, "#FDEF71"),
            QazaViewData("Аср", 3453, 44344, R.drawable.ic_asr, "#FBCD89"),
            QazaViewData("Шам", 4355, 43455, R.drawable.ic_magrib, "#F5CAC4"),
            QazaViewData("Құптан", 7677, 44345, R.drawable.ic_isha, "#C2D1DF"),
            QazaViewData("Үтір", 456, 6437, R.drawable.ic_utir, "#AEAEAE"),
        )
        Box(Modifier.background(Color.White)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(qazaInfoList) { qazaInfo ->
                    QazaCard(qazaInfo)
                }
            }
        }
    }


    @Composable
    private fun QazaCard(
        qazaViewData: QazaViewData
    ) {
        Card(
            backgroundColor = Color(android.graphics.Color.parseColor("#F6F4F4"))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                QazaTitle(qazaViewData = qazaViewData)
                Spacer(modifier = Modifier.height(10.dp))
                SolatInfo(
                    solatTitle = stringResource(id = R.string.qaza_remainder),
                    solatCount = qazaViewData.count
                )
                Spacer(modifier = Modifier.height(4.dp))
                SolatInfo(
                    solatTitle = stringResource(id = R.string.sapar_qaza_remainder),
                    solatCount = qazaViewData.saparCount
                )
                Spacer(modifier = Modifier.height(12.dp))
                ChangeQazaButton()
            }
        }
    }

    @Composable
    private fun QazaTitle(
        qazaViewData: QazaViewData
    ) {
        Row {
            Image(
                painter = painterResource(id = qazaViewData.icon),
                contentDescription = "solat icon",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(android.graphics.Color.parseColor("#FAF3B7")))
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = qazaViewData.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.alpha(0.65f)
            )
        }
    }

    @Composable
    private fun SolatInfo(
        solatTitle: String,
        solatCount: Int
    ) {
        Row(
            modifier = Modifier
                .height(34.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = solatTitle,
                fontSize = 13.sp,
                color = Color.Black,
                modifier = Modifier
                    .alpha(0.35f)
                    .weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$solatCount",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.alpha(0.65f),
            )
        }
    }

    @Composable
    private fun ChangeQazaButton() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .height(48.dp)
                .background(
                    color = Color(
                        android.graphics.Color.parseColor("#262ACB4E")
                    )
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        color = Color.Black
                    ),
                    onClick = {

                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.action_change),
                fontSize = 18.sp,
                color = Color(
                    android.graphics.Color.parseColor("#4D8843")
                ),
                modifier = Modifier.alpha(0.80f)
            )
        }
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
            Observer { qazaDataList -> qazaProgressAdapter.setList(qazaDataList) })
        qazaProgressViewModel.getQazaProgressLiveData().observe(
            viewLifecycleOwner,
            Observer { qazaProgressData ->
                completedQazaTextView.text = String.format(
                    getString(R.string.completed_qaza_fmt),
                    "${roundOffDecimal(qazaProgressData.completedPercent)}"
                )
                totalPrayedQazaTextView.text = String.format(
                    getString(R.string.total_prayed_qaza),
                    qazaProgressData.totalPreyedCount
                )
                totalRemainTextView.text = String.format(
                    getString(R.string.total_remain_qaza),
                    qazaProgressData.totalRemainCount
                )
                mainProgressBar.progress = 100 - qazaProgressData.completedPercent.toInt()
            }
        )
        qazaProgressViewModel.getCalculatedRemainTime().observe(
            viewLifecycleOwner,
            Observer { calculatedRemainTime ->
                calculatedTimeTextView.text = String.format(
                    getString(R.string.progress_assumption_info),
                    calculatedRemainTime
                )
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