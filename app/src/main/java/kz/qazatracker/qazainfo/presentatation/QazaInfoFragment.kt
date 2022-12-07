package kz.qazatracker.qazainfo.presentatation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterialApi::class)
class QazaInfoFragment : Fragment() {

    private val qazaInfoViewModel: QazaInfoViewModel by viewModel()

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
                qazaList = qazaList.value,
                coroutineScope = coroutineScope,
                sheetState = sheetState
            )
        }
    }

    @Composable
    private fun QazaInfoContent(
        qazaList: List<QazaViewData>,
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
                    items(qazaList) { qazaViewData ->
                        QazaCard(
                            qazaViewData,
                            onItemClick = {
                                coroutineScope.launch {
                                    qazaInfoViewModel.onQazaChangeClick(qazaViewData)
                                    sheetState.show()
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
        val qazaViewData: QazaViewData? =
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
        qazaViewData: QazaViewData,
        onItemClick: () -> Unit
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color(android.graphics.Color.parseColor("#F6F4F4"))
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
            ) {
                QazaTitle(qazaViewData = qazaViewData)
                Spacer(modifier = Modifier.height(8.dp))
                ChangeQazaButton(
                    solatCount = qazaViewData.count,
                    saparSolatCount = qazaViewData.saparCount,
                    onItemClick = { onItemClick() }
                )
            }
        }
    }

    @Composable
    private fun QazaTitle(
        qazaViewData: QazaViewData
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = qazaViewData.icon),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.qaza_icon_bg_color))
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = qazaViewData.name,
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
                    indication = rememberRipple(color = Color.Black),
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
                    color = Color.Black,
                    modifier = Modifier.alpha(0.35f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = "${solatCount + saparSolatCount}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
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