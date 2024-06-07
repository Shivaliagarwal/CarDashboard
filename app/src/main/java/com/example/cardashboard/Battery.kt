package com.example.cardashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Batterymain() :Int{
    var battery: Int=1
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart// Aligns the content to the top-right corner
    ) {
        Button(onClick = {
                         if(battery<4)
                         {
                             battery=battery+1;
                         }
        },

            colors = ButtonDefaults.buttonColors(Color(250,135,40))

            , modifier = Modifier
                .padding(top = 180.dp, start = 60.dp)
                .height(50.dp)
                .width(150.dp)) {
            Text(text = "Charge $battery")

        }
        Text(
            text = "$battery",
            style = TextStyle(color = Color.White),
            fontSize = 16.sp,
            // Smaller font size for KM/HR
            fontWeight = FontWeight.Thin)
    }
    return battery;
}