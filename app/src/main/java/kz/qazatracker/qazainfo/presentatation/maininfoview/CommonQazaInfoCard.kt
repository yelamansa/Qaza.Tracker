package kz.qazatracker.qazainfo.presentatation.maininfoview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.qazatracker.R
import kz.qazatracker.qazainfo.presentatation.model.TotalQazaState

@Preview
@Composable
fun show() {
    CommonQazaInfo(totalQazaState = TotalQazaState(
            345,4343
    ))
}

@Composable
fun CommonQazaInfo(
        totalQazaState: TotalQazaState
) {
    Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White,
            modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
        ) {
            CommonQazaInfoItem(
                    count = totalQazaState.totalCompletedCount,
                    color = colorResource(id = R.color.qaza_change_button_bg),
                    modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier
                    .width(1.dp)
                    .height(36.dp)
                    .background(Color.LightGray.copy(alpha = 0.3f)))

            CommonQazaInfoItem(
                    count = totalQazaState.totalRemainCount,
                    color = Color.Red,
                    modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CommonQazaInfoItem(
        count: Int,
        color: Color,
        modifier: Modifier,
) {
    Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
    ) {
        Row(
                verticalAlignment = Alignment.CenterVertically
        ) {
            Canvas(modifier = Modifier.size(6.dp), onDraw = {
                drawCircle(color)
            })
            Text(
                    text = count.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                            .alpha(0.75f)
                            .padding(start = 4.dp),
            )
        }
        Text(
                text = stringResource(id = R.string.total_remaining_qaza),
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                        .alpha(0.75f)
                        .padding(start = 4.dp),
        )
    }
}