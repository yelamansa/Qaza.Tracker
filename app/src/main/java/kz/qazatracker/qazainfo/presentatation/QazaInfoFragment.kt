package kz.qazatracker.qazainfo.presentatation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kz.qazatracker.R
import kz.qazatracker.qazainfo.presentatation.model.QazaInfoData
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class QazaInfoFragment: Fragment() {

    private val qazaInfoViewModel: QazaInfoViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            QazaInfoScreen()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        qazaInfoViewModel.onCreate()
    }

    @Preview
    @Composable
    fun QazaInfoScreenPreview() {
        QazaInfoScreen()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun QazaInfoScreen() {
        val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val coroutineScope = rememberCoroutineScope()
        BackHandler(sheetState.isVisible) {
            coroutineScope.launch { sheetState.hide() }
        }
        val qazaList = qazaInfoViewModel.getQazaInfoListLiveData().observeAsState(emptyList())
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetContent = { QazaChangeBottomSheetContent() },
            sheetShape = RoundedCornerShape(16.dp)
        ) {
            QazaInfoContent(
                qazaInfoList = qazaList.value,
                coroutineScope = coroutineScope,
                sheetState = sheetState
            )
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun QazaInfoContent(
        qazaInfoList: List<QazaInfoData>,
        coroutineScope: CoroutineScope,
        sheetState: ModalBottomSheetState
    ) {
        Column(
            modifier = Modifier
                    .fillMaxHeight()
                    .background(colorResource(id = R.color.qaza_info_bg)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(items = qazaInfoList, itemContent = { qazaInfoData: QazaInfoData ->
                        when(qazaInfoData) {
                            is QazaInfoData.FastingQazaViewData -> {
                                QazaCard(qazaViewData = qazaInfoData) {
                                    coroutineScope.launch {
                                        qazaInfoViewModel.onQazaChangeClick(qazaInfoData)
                                        sheetState.show()
                                    }
                                }
                            }
                            is QazaInfoData.SolatQazaViewData -> {
                                QazaCard(qazaViewData = qazaInfoData) {
                                    coroutineScope.launch {
                                        qazaInfoViewModel.onQazaChangeClick(qazaInfoData)
                                        sheetState.show()
                                    }
                                }
                            }
                        }
                    }
                    )
                }
            }
            Box(
                contentAlignment = Alignment.BottomCenter
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dots),
                    contentDescription = null,
                    modifier = Modifier
                            .padding(16.dp)
                            .clip(CircleShape)
                            .alpha(0.55f)
                            .background(Color.White)
                            .clickable {
                                qazaInfoViewModel.onMenuClick()
                            }
                            .padding(12.dp)
                )
            }
        }
    }

    @Composable
    private fun QazaChangeBottomSheetContent() {
        val qazaViewData: QazaInfoData? =
            qazaInfoViewModel.getQazaChangeLiveData().observeAsState(null).value
        when (qazaViewData) {
            is QazaInfoData.FastingQazaViewData -> {
                FastingQazaChangeBottomSheet(
                        qazaViewData,
                        qazaInfoViewModel
                )
            }
            is QazaInfoData.SolatQazaViewData -> {
                SolatQazaChangeBottomSheet(
                        qazaViewData,
                        qazaInfoViewModel
                )
            }
            null -> Text(text = stringResource(id = R.string.error_loading_qaza))
        }
    }


    @Composable
    private fun QazaCard(
            qazaViewData: QazaInfoData,
            onItemClick: () -> Unit
    ) {
        Card(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.White,
                modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                    top = 12.dp, bottom = 8.dp, start = 8.dp, end = 8.dp
                            )
            ) {
                when (qazaViewData) {
                    is QazaInfoData.SolatQazaViewData -> {
                        QazaCardContent(
                                name = qazaViewData.name,
                                qazaRemainCount = qazaViewData.remainCount,
                                qazaCompletedCount = qazaViewData.completedCount,
                                onItemClick = onItemClick
                        )
                    }
                    is QazaInfoData.FastingQazaViewData -> {
                        QazaCardContent(
                                name = qazaViewData.name,
                                qazaRemainCount = qazaViewData.remainCount,
                                qazaCompletedCount = qazaViewData.completedCount,
                                onItemClick = onItemClick
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun QazaCardContent(
            name: String,
            qazaRemainCount: Int,
            qazaCompletedCount: Int,
            onItemClick: () -> Unit
    ) {
        QazaTitle(
                name = name,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Column() {
            Text(
                    text = stringResource(id = R.string.qaza_remainder),
                    fontSize = 13.sp,
                    color = Color.Black,
                    modifier = Modifier
                            .alpha(0.35f),
                    maxLines = 1
            )
            Row(
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Canvas(modifier = Modifier.size(6.dp), onDraw = {
                    drawCircle(color = Color.Red)
                })
                Text(
                        text = "$qazaRemainCount",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                                .alpha(0.75f)
                                .padding(start = 4.dp),
                )
            }
        }
        Column {
            Text(
                    text = stringResource(id = R.string.qaza_completed),
                    fontSize = 13.sp,
                    color = Color.Black,
                    modifier = Modifier.alpha(0.35f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
            )
            Row(
                    verticalAlignment = Alignment.CenterVertically
            ) {
                val color = colorResource(id = R.color.qaza_change_button_bg)
                Canvas(modifier = Modifier.size(6.dp), onDraw = {
                    drawCircle(color = color)
                })
                Text(
                        text = "$qazaCompletedCount",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                                .alpha(0.75f)
                                .padding(start = 4.dp),
                )
            }
        }
        ChangeQazaButton(
                onItemClick = { onItemClick() }
        )
    }

    @Composable
    private fun QazaTitle(
            name: String,
    ) {
        Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color.Black,
                modifier = Modifier.alpha(0.65f)
        )
    }

    @Composable
    private fun ChangeQazaButton(
            onItemClick: () -> Unit
    ) {
        Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = colorResource(id = R.color.qaza_change_button_bg))
                        .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(color = Color.Black),
                                onClick = onItemClick
                        )
                        .padding(vertical = 12.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                    text = stringResource(id = R.string.action_change),
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.alpha(0.75f),
            )
        }
    }
}