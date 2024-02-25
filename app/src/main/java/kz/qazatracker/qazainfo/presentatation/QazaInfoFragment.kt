package kz.qazatracker.qazainfo.presentatation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kz.qazatracker.R
import kz.qazatracker.qazainfo.presentatation.maininfoview.CommonQazaInfo
import kz.qazatracker.qazainfo.presentatation.model.QazaInfoStateData
import kz.qazatracker.qazainfo.presentatation.model.QazaState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class QazaInfoFragment : Fragment() {

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
        val qazaState = qazaInfoViewModel.getQazaInfoStateLiveData().observeAsState()
        ModalBottomSheetLayout(
                sheetState = sheetState,
                sheetContent = { QazaChangeBottomSheetContent() },
                sheetShape = RoundedCornerShape(16.dp)
        ) {
            val state = qazaState.value ?: return@ModalBottomSheetLayout

            QazaInfoContent(
                    qazaState = state,
                    coroutineScope = coroutineScope,
                    sheetState = sheetState
            )
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun QazaInfoContent(
            qazaState: QazaInfoStateData,
            coroutineScope: CoroutineScope,
            sheetState: ModalBottomSheetState
    ) {
        Column(
                modifier = Modifier
                        .fillMaxHeight()
                        .background(colorResource(id = R.color.qaza_info_bg)),
                horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Column(
                    modifier = Modifier.weight(1f).padding(16.dp),
                    verticalArrangement = Arrangement.Center
            ) {
                CommonQazaInfo(totalQazaState = qazaState.totalQazaState)
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(items = qazaState.qazaList, itemContent = { qazaState: QazaState ->
                        QazaCardCircular(
                                qazaInfoData = qazaState) {
                            coroutineScope.launch {
                                qazaInfoViewModel.onQazaChangeClick(qazaState)
                                sheetState.show()
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
        val qazaViewData: QazaState? =
                qazaInfoViewModel.getQazaChangeLiveData().observeAsState(null).value
        if (qazaViewData != null) {
            QazaChangeBottomSheet(
                    qazaViewData,
                    qazaInfoViewModel
            )
        } else {
            Text(text = stringResource(id = R.string.error_loading_qaza))
        }
    }
}

    @Composable
    private fun QazaCardCircular(
            qazaInfoData: QazaState,
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
                                    top = 12.dp, bottom = 12.dp, start = 4.dp, end = 4.dp
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QazaCircleProgress(
                        title = qazaInfoData.name,
                        completedCount = qazaInfoData.completedCount,
                        remainCount = qazaInfoData.remainCount,
                        editAction = { onItemClick() }
                )
                Spacer(modifier = Modifier.height(8.dp))
                ChangeQazaButton(onItemClick)
            }
        }
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