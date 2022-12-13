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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.qazatracker.R
import kz.qazatracker.qazainfo.presentatation.model.QazaInfoData

@Composable
fun SolatQazaChangeBottomSheet(
    qazaViewData: QazaInfoData.SolatQazaViewData,
    onQazaValueUpdate: (qazaKey: String, value: Int) -> Unit
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
        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .width(50.dp)
                .height(5.dp)
                .align(Alignment.CenterHorizontally)
                .background(colorResource(id = R.color.qaza_change_container_bg))
        )
        Spacer(modifier = Modifier.height(16.dp))
        ChangeModalTitle(
            name = qazaViewData.name,
            count = qazaViewData.count + qazaViewData.saparCount
        )
        Spacer(modifier = Modifier.height(8.dp))
        ChangeQazaContainer(
            qazaKey = qazaViewData.key,
            name = stringResource(id = R.string.qazas),
            count = qazaViewData.count,
            isExpended = if (qazaViewData.hasSapar) changeQazaIsExpended else null,
            onQazaValueUpdate = onQazaValueUpdate
        )
        Spacer(modifier = Modifier.height(8.dp))
        if(qazaViewData.hasSapar) {
            ChangeQazaContainer(
                qazaKey = qazaViewData.getSaparKey(),
                name = stringResource(id = R.string.sapar_qazas),
                count = qazaViewData.saparCount,
                isExpended = changeSaparQazaIsExpended,
                onQazaValueUpdate = onQazaValueUpdate
            )
        }
    }
}

@Composable
fun FastingQazaChangeBottomSheet(
    qazaViewData: QazaInfoData.FastingQazaViewData,
    onQazaValueUpdate: (qazaKey: String, value: Int) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .width(50.dp)
                .height(5.dp)
                .align(Alignment.CenterHorizontally)
                .background(colorResource(id = R.color.qaza_change_container_bg))
        )
        Spacer(modifier = Modifier.height(16.dp))
        ChangeModalTitle(
            name = qazaViewData.name,
            count = qazaViewData.сount
        )
        Spacer(modifier = Modifier.height(8.dp))
        ChangeQazaContainer(
            qazaKey = qazaViewData.key,
            name = stringResource(id = R.string.qazas),
            count = qazaViewData.сount,
            onQazaValueUpdate = onQazaValueUpdate
        )
    }
}

@Composable
fun ChangeQazaContainer(
    qazaKey: String,
    name: String,
    count: Int,
    onQazaValueUpdate: (qazaKey: String, value: Int) -> Unit,
    isExpended: MutableState<Boolean>? = null,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.qaza_change_container_bg))
            .padding(16.dp)
    ) {
        if (isExpended != null) {
            QazaChangeContainerTitle(
                name = name,
                count = count,
                isExpended = isExpended
            )
        }
        if (isExpended?.value != false) {
            Spacer(modifier = Modifier.height(8.dp))
            QazaButtons(
                qazaKey = qazaKey,
                onQazaValueUpdate = onQazaValueUpdate,
            )
        }
    }
}

@Composable
fun QazaChangeContainerTitle(
    name: String,
    count: Int,
    isExpended: MutableState<Boolean>? = null,
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
                    if (isExpended == null) return@clickable

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
            if (isExpended != null) {
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
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "$count",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.alpha(0.65f)
            )
        }
    }
}

@Composable
fun QazaButtons(
    qazaKey: String,
    onQazaValueUpdate: (qazaKey: String, value: Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ChangeQazaButton(
            text = stringResource(id = R.string.increate_one_qaza),
            weight = 1f,
            color = colorResource(id = R.color.qaza_decrees_button_bg),
            onClick = { onQazaValueUpdate(qazaKey, +1) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        ChangeQazaButton(
            text = stringResource(id = R.string.decrease_one_qaza),
            weight = 2f,
            color = colorResource(id = R.color.qaza_change_button_bg),
            onClick = { onQazaValueUpdate(qazaKey, -1) }
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
fun ChangeModalTitle(
    name: String,
    count: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.alpha(0.65f)
        )
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$count",
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