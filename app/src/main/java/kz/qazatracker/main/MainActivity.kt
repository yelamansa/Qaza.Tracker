package kz.qazatracker.main

import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kz.qazatracker.R
import kz.qazatracker.qazainfo.presentatation.QazaChangeBottomSheet
import kz.qazatracker.qazainfo.presentatation.QazaInfoViewModel
import kz.qazatracker.qazainfo.presentatation.model.QazaInfoData
import kz.qazatracker.remoteconfig.RemoteConfig
import kz.qazatracker.utils.BaseActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val qazaInfoViewModel: QazaInfoViewModel by viewModel()
    private val remoteConfig: RemoteConfig by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QazaInfoScreen()
        }
        qazaInfoViewModel.onCreate()
        remoteConfig.fetchAndActivate(this)
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
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(qazaInfoList) { qazaInfoData ->
                        QazaCard(
                            qazaInfoData,
                            onItemClick = {
                                coroutineScope.launch {
                                    when(qazaInfoData) {
                                        is QazaInfoData.SolatQazaViewData -> {
                                            qazaInfoViewModel.onQazaChangeClick(qazaInfoData)
                                            sheetState.show()
                                        }
                                        is QazaInfoData.FastingQazaViewData -> TODO()
                                        is QazaInfoData.QazaReadingViewData -> TODO()
                                    }
                                }
                            }
                        )
                    }
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
                        .background(Color(android.graphics.Color.parseColor("#F6F4F4")))
                        .clickable {

                        }
                        .padding(12.dp)
                )
            }
        }
    }

    @Composable
    private fun QazaChangeBottomSheetContent() {
        val qazaViewData: QazaInfoData.SolatQazaViewData? =
            qazaInfoViewModel.getQazaChangeLiveData().observeAsState(null).value
        if (qazaViewData != null) {
            QazaChangeBottomSheet(
                qazaViewData = qazaViewData,
                onQazaValueIncrement = { isSapar ->
                    qazaInfoViewModel.onQazaValueIncrement(qazaViewData.key, isSapar)
                },
                onQazaValueDecrement = { isSapar ->
                    qazaInfoViewModel.onQazaValueDecrement(qazaViewData.key, isSapar)
                }
            )
        } else {
            Text(text = stringResource(id = R.string.error_loading_qaza))
        }
    }

    @Composable
    private fun QazaCard(
        qazaViewData: QazaInfoData,
        onItemClick: () -> Unit
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color(android.graphics.Color.parseColor("#F6F4F4"))
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
            ) {
                when(qazaViewData) {
                    is QazaInfoData.SolatQazaViewData -> {
                        QazaTitle(
                            name = qazaViewData.name,
                            icon = qazaViewData.icon
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        ChangeQazaButton(
                            solatCount = qazaViewData.count,
                            saparSolatCount = qazaViewData.saparCount,
                            onItemClick = { onItemClick() }
                        )
                    }
                    is QazaInfoData.FastingQazaViewData -> {}
                    is QazaInfoData.QazaReadingViewData -> {}
                }
            }
        }
    }

    @Composable
    private fun QazaTitle(
        name: String,
        @DrawableRes icon: Int,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.qaza_icon_bg_color))
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.alpha(0.65f)
            )
        }
    }

    @Composable
    private fun ChangeQazaButton(
        solatCount: Int,
        saparSolatCount: Int,
        onItemClick: () -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(color = colorResource(id = R.color.qaza_change_button_bg))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = androidx.compose.ui.graphics.Color.Black),
                    onClick = onItemClick
                )
                .padding(vertical = 16.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.qaza_remainder),
                    fontSize = 12.sp,
                    color = androidx.compose.ui.graphics.Color.Black,
                    modifier = Modifier.alpha(0.35f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = "${solatCount + saparSolatCount}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color.Black,
                    modifier = Modifier.alpha(0.75f),
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null
            )
        }
    }
}
