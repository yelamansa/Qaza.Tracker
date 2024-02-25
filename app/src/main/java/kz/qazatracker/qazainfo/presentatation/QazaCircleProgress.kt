package kz.qazatracker.qazainfo.presentatation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.qazatracker.R
import kz.qazatracker.utils.Test
import kz.qazatracker.utils.TextPrimary


@Composable
fun QazaCircleProgress(
        title: String,
        completedCount: Int,
        remainCount: Int,
        editAction: () -> Unit,
        size: Dp = 86.dp,
        indicatorThickness: Dp = 9.dp,
        animationDuration: Int = 1000,
        titleTextSize: TextUnit = 14.sp,
        subTitleTextSize: TextUnit = 20.sp,
        foregroundIndicatorColor: Color = colorResource(id = R.color.qaza_change_button_bg),
        backgroundIndicatorColor: Color = Color.LightGray.copy(alpha = 0.3f),
) {
    val percent = (completedCount * 100) / (remainCount.toFloat() + completedCount)
    var numberR by remember {
        mutableStateOf(0f)
    }
    val animateNumber = animateFloatAsState(
            targetValue = numberR,
            animationSpec = tween(
                    durationMillis = animationDuration,
            )
    )
    LaunchedEffect(Unit) {
        numberR = percent
    }
    Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, fontSize = 18.sp)
        Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                        .size(size = size + 20.dp)
        ) {
            Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                            .size(size = size)
            ) {
                Canvas(
                        modifier = Modifier
                                .size(size = size)
                ) {
                    drawCircle(
                            color = backgroundIndicatorColor,
                            radius = size.toPx() / 2,
                            style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round)
                    )
                    val sweepAngle = (animateNumber.value / 100) * 360
                    drawArc(
                            color = foregroundIndicatorColor,
                            startAngle = -90f,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            style = Stroke(indicatorThickness.toPx(), cap = StrokeCap.Round)
                    )
                }
                Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                            verticalAlignment = Alignment.CenterVertically
                    ) {
                        val color = colorResource(id = R.color.qaza_change_button_bg)
                        Canvas(modifier = Modifier.size(6.dp), onDraw = {
                            drawCircle(color = color)
                        })
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                                text = "$completedCount",
                                textAlign = TextAlign.Center,
                                fontSize = titleTextSize,
                                color = Color.Gray
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Canvas(modifier = Modifier.size(6.dp), onDraw = {
                            drawCircle(color = Color.Red)
                        })
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                                text = "$remainCount",
                                textAlign = TextAlign.Center,
                                fontSize = subTitleTextSize,
                                color = Color.TextPrimary
                        )
                    }
                }
            }

        }
    }
}