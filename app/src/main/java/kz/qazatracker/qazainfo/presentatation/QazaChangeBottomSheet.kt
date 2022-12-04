package kz.qazatracker.qazainfo.presentatation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.qazatracker.R

@Preview
@Composable
fun QazaChangeDialog() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        QazaChangeBottomSheet(
            qazaViewData = QazaViewData(
                name = "Таң",
                count = 4445,
                saparCount = 54,
                icon = R.drawable.ic_fajr
            )
        )
    }
}

@Composable
fun QazaChangeBottomSheet(
    qazaViewData: QazaViewData
) {
    val changeQazaIsExpended: MutableState<Boolean> = remember { mutableStateOf(true) }
    val changeSaparQazaIsExpended: MutableState<Boolean> = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
    ) {
        ChangeModalTitle(qazaViewData)
        Spacer(modifier = Modifier.height(8.dp))
        ChangeQazaContainer(
            name = stringResource(id = R.string.qazas),
            count = qazaViewData.count,
            isExpended = changeQazaIsExpended,
            onIncrementClick = {},
            onDecrementClick = {}
        )
        Spacer(modifier = Modifier.height(8.dp))
        ChangeQazaContainer(
            name = stringResource(id = R.string.sapar_qazas),
            count = qazaViewData.count,
            isExpended = changeSaparQazaIsExpended,
            onIncrementClick = {},
            onDecrementClick = {}
        )
    }
}

@Composable
fun ChangeQazaContainer(
    name: String,
    count: Int,
    isExpended: MutableState<Boolean>,
    onIncrementClick: () -> Unit,
    onDecrementClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.qaza_change_container_bg))
            .padding(16.dp)
    ) {
        QazaChangeContainerTitle(
            name = name,
            count = count,
            isExpended = isExpended
        )
        if (isExpended.value) {
            Spacer(modifier = Modifier.height(8.dp))
            QazaButtons(
                onIncrementClick = onIncrementClick,
                onDecrementClick = onDecrementClick
            )
        }
    }
}

@Composable
fun QazaChangeContainerTitle(
    name: String,
    count: Int,
    isExpended: MutableState<Boolean>,
) {
    Row {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    isExpended.value = !isExpended.value
                }
        ) {
            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.alpha(0.45f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_bottom),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 5.dp, start = 8.dp)
                    .size(16.dp)
                    .alpha(0.45f)
                    .rotate(animateFloatAsState(if (isExpended.value) 180f else 0f).value)
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clickable {

                }
        ) {
            Text(
                text = "$count",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.alpha(0.65f)
            )
            if (isExpended.value) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = R.string.action_change),
                        fontSize = 13.sp,
                        color = Color.Black,
                        modifier = Modifier.alpha(0.35f)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(top = 2.dp)
                            .width(10.dp)
                            .height(24.dp)
                            .alpha(0.55f)
                    )
                }
            }
        }
    }
}

@Composable
fun QazaButtons(
    onIncrementClick: () -> Unit,
    onDecrementClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ChangeQazaButton(
            text = stringResource(id = R.string.increate_one_qaza),
            weight = 1f,
            color = colorResource(id = R.color.qaza_decrees_button_bg),
            onClick = onDecrementClick
        )
        Spacer(modifier = Modifier.width(8.dp))
        ChangeQazaButton(
            text = stringResource(id = R.string.decrease_one_qaza),
            weight = 2f,
            color = colorResource(id = R.color.qaza_change_button_bg),
            onClick = onIncrementClick
        )
    }
}

@Composable
fun RowScope.ChangeQazaButton(
    text: String,
    weight: Float,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(weight)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Color.Black),
                onClick = onClick
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.alpha(0.65f)
        )
    }
}

@Composable
fun ChangeModalTitle(qazaViewData: QazaViewData) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = qazaViewData.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.alpha(0.65f)
        )
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${qazaViewData.count + qazaViewData.saparCount}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.alpha(0.65f)
            )
            Text(
                text = stringResource(id = R.string.all_remaining_qaza),
                fontSize = 12.sp,
                color = Color.Black,
                modifier = Modifier.alpha(0.35f)
            )
        }
    }
}