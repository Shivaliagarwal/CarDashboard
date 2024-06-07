package com.example.cardashboard
import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

private val INDICATOR_LENGTH=18.dp
private val MINOR_INDICATOR_LENGTH=14.dp
private val INDICATOR_INITIAL_OFFSET=5.dp



@Composable
fun SpeedometerScreen(@FloatRange(0.00,240.00)currentSpeed:Float) {
    val textMeasurer: TextMeasurer = rememberTextMeasurer()
    Canvas(modifier = Modifier
        .padding(90.dp)
        .requiredSize(360.dp)) {
        drawArc(
            color = Color(250,135,40),
            startAngle = 30f,
            sweepAngle = -240f,
            useCenter = false,
            style = Stroke(width = 2.0.dp.toPx())
        )
        for(angle in 300 downTo 60){
            val speed=300-angle
            val startOffset= pointOnCircle(thetaInDegrees = angle.toDouble(),
                radius = size.height / 2 - INDICATOR_INITIAL_OFFSET.toPx(),
                cX = center.x,
                cY = center.y
            )
            if(speed%20==0)
            {
                val endOffset = pointOnCircle(
                    thetaInDegrees = angle.toDouble(),
                    // Length of minor indicator
                    radius = size.height / 2 - INDICATOR_LENGTH.toPx(),
                    cX = center.x,
                    cY = center.y
                )
                drawLine(color = Color.White,startOffset,endOffset, strokeWidth = 2.dp.toPx())
                val textLayoutResult=textMeasurer.measure(speed.toString(), style = TextStyle.Default)
                val textWidth=textLayoutResult.size.width
                val textHeight=textLayoutResult.size.height
                val textOffset=pointOnCircle(
                    thetaInDegrees = angle.toDouble(),
                    // Length of minor indicator
                    radius = size.height / 2 - INDICATOR_LENGTH.toPx()-textWidth/2 - INDICATOR_INITIAL_OFFSET.toPx(),
                    cX = center.x-textWidth/2,
                    cY = center.y-textHeight/2
                )
                drawText(
                    textLayoutResult = textLayoutResult,
                    color = Color.White,
                    topLeft = textOffset
                )

            }
            else if(speed%10==0)
            {
                val endOffset = pointOnCircle(
                    thetaInDegrees = angle.toDouble(),
                    // Length of minor indicator
                    radius = size.height / 2 - MINOR_INDICATOR_LENGTH.toPx(),
                    cX = center.x,
                    cY = center.y
                )
                drawLine(color = Color(250,135,40),startOffset,endOffset, strokeWidth = 2.dp.toPx())

            }
            else{
                val endOffset = pointOnCircle(
                    thetaInDegrees = angle.toDouble(),
                    // Length of minor indicator
                    radius = size.height / 2 - MINOR_INDICATOR_LENGTH.toPx(),
                    cX = center.x,
                    cY = center.y
                )
                drawLine(color = Color.White,startOffset,endOffset)

            }


        }
        val currentSpeedOffset=pointOnCircle(thetaInDegrees =300- currentSpeed.toDouble(),
            radius = size.height /2 - INDICATOR_LENGTH.toPx()-4.dp.toPx() ,
            cX = center.x,
            cY = center.y
        )
        drawLine(color = Color(250,135,40), start = center, end = currentSpeedOffset, strokeWidth = 4.dp.toPx(), cap = StrokeCap.Butt, alpha = 0.6f)

    }

}
@Composable
fun SpeedText(currentSpeed:Float){
    val
    currentSpeedInt=currentSpeed.toInt()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Current speed text
            Text(
                text = currentSpeedInt.toString(),
                style = TextStyle(color = Color.White),
                fontSize = 95.sp,
                // Larger font size for current speed
                letterSpacing = 8.5.sp,
                fontWeight = FontWeight.Light,
                fontFamily = FontFamily.Cursive
,
                        modifier = Modifier.wrapContentSize()
            )

            // KM/HR text
            Text(
                text = "KM/HR",
                style = TextStyle(color = Color.White),
                fontSize = 16.sp,
                // Smaller font size for KM/HR
                fontWeight = FontWeight.Thin,
                modifier = Modifier.wrapContentSize()
            )
        }
    }

}
@Composable
fun TimeAndDateUI() {
    // Create LiveData to hold time and date
    val currentTimee = Calendar.getInstance().time
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    var formattedTime = sdf.format(currentTimee)
    formattedTime = formattedTime.replace("am", "AM").replace("pm", "PM")
    val currentDate: LiveData<String> = MutableLiveData(SimpleDateFormat("d MMM",Locale.getDefault()).format(Date()))

    // Observe LiveData changes
    val currentTime: LiveData<String> = MutableLiveData(formattedTime)
    val currentTimeStr by currentTime.observeAsState()
    val currentDateStr by currentDate.observeAsState()

    Column(modifier = Modifier.padding(50.dp)) {
        // Display time
        Text(text = "$currentTimeStr",
            style = TextStyle(color = Color(250,135,40) ,
                fontSize = 25.sp,
                fontFamily = FontFamily.Cursive
                )
        )

        // Display date
        Text(text = "$currentDateStr",
            style = TextStyle(color = Color.White ),
            fontSize = 18.sp,
            fontFamily = FontFamily.Cursive
        )
    }
}
private fun pointOnCircle(
    thetaInDegrees: Double,
    radius: Float,
    cX: Float,
    cY: Float,
): Offset {
    val x = cX + (radius * sin(Math.toRadians(thetaInDegrees)).toFloat())
    val y = cY + (radius * cos(Math.toRadians(thetaInDegrees)).toFloat())

    return Offset(x, y)
}
@Composable
fun Battery(battery:Int) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = BottomStart // Align content to the bottom left
    ) {
        Row {
            var percent: Int = when (battery) {
                1 -> 25
                2 -> 50
                3 -> 75
                else -> 100
            }
            Text(
                text = "BATTERY $percent%",
                style = TextStyle(color = Color.White),
                fontSize = 25.sp,
                modifier = Modifier.offset(x = 70.dp, y = (-40).dp),
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.ExtraLight,
                letterSpacing = 3.sp// Set position from bottom-left
            )

            Canvas(modifier = Modifier) {
                val lineHeight = 16.sp.toPx() // Length of each line (1 cm)
                val startY = size.height - 16.sp.toPx()
                for (i in 0 until battery) {
                    drawLine(
                        color = Color(250,135,40),
                        start = Offset(100.dp.toPx() + i * (lineHeight + 8.dp.toPx()), startY),
                        end = Offset(100.dp.toPx() + (i + 1) * lineHeight + i * (8.dp.toPx()), startY),
                        strokeWidth = 7.dp.toPx(), // Adjust thickness of the line as needed
                        cap = StrokeCap.Round
                    )
                }
                for (i in battery until 4) {
                    drawLine(
                        color = Color.White,
                        start = Offset(100.dp.toPx() + i * (lineHeight + 8.dp.toPx()), startY),
                        end = Offset(100.dp.toPx() + (i + 1) * lineHeight + i * (8.dp.toPx()), startY),
                        strokeWidth = 7.dp.toPx(), // Adjust thickness of the line as needed
                        cap = StrokeCap.Round
                    )
                }
            }
        }


        }
}


