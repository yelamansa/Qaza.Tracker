package kz.qazatracker.qazainfo.presentatation.maininfoview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.qazatracker.R

@Composable
fun CommonQazaInfoCard(
        totalQazaRemainCount: Int,
        totalQazaCompletedCount: Int,
        color: Color
) {
    Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White,
            modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                    text = stringResource(id = R.string.total_remain_qaza),
                    fontSize = 13.sp,
                    color = Color.Black,
                    modifier = Modifier.alpha(0.35f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
            )
            Row(
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Canvas(modifier = Modifier.size(6.dp), onDraw = {
                    drawCircle(color = color)
                })
                Text(
                        text = "$totalQazaRemainCount",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                                .alpha(0.75f)
                                .padding(start = 4.dp),
                )
            }
        }
    }
}