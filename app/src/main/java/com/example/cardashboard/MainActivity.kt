package com.example.cardashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cardashboard.ui.theme.CarDashboardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarDashboardTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    var s:Float=  CarDashboard()
                    //var new:Int=Batterymain()

                    SpeedometerScreen(s)
                    TimeAndDateUI()
                    SpeedText(s)
                    Battery(3)



                }

                }
            }
        }

    }



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SpeedometerPreview(){
    MaterialTheme{
        SpeedometerScreen(40f)
        TimeAndDateUI()
        SpeedText(40f)
        Battery(3)
    }
}




