package com.example.cardashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun CarDashboard():Float {
    var speedRecord by remember {
        mutableStateOf(0)
    }
    var isPressedSpeed by remember {
        mutableStateOf(false)
    }
    var isPressedBreak by remember {
        mutableStateOf(false)
    }
    var fuel by remember {
        mutableStateOf(100)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd // Aligns the content to the top-right corner
        ) {
            Button(
                onClick = {
                    if (isPressedSpeed) {
                        isPressedSpeed = false
                    } else {
                        isPressedSpeed = true
                    }
                },
                colors = ButtonDefaults.buttonColors(Color(250,135,40))

                , modifier = Modifier
                    .padding(top = 90.dp, end = 50.dp)
                    .height(50.dp)
                    .width(150.dp)
            ) {
                Text(text = "Accelerator")
            }
            Button(onClick = {
                if (isPressedBreak) {
                    isPressedBreak = false
                } else {
                    isPressedBreak = true
                }
            },
                colors = ButtonDefaults.buttonColors(Color(250,135,40))
                ,
                modifier = Modifier
                    .padding(top = 200.dp, end = 50.dp)
                    .height(50.dp)
                    .width(150.dp) // Adjust size as needed

            ) {
                Text(text = "Break")
            }
        }




    }

    LaunchedEffect(isPressedSpeed || isPressedBreak) {
        if (isPressedSpeed) {
            while (isPressedSpeed && !(isPressedBreak)) {
                delay(100)
                if (speedRecord < 240) speedRecord += 1
            }
            if(isPressedBreak){
                while (isPressedBreak) {
                    delay(100)
                    if (speedRecord > 0) speedRecord -= 1
                }
            }

        } else if (isPressedBreak) {

            while (isPressedBreak) {
                delay(100)
                if (speedRecord > 0) speedRecord -= 1
            }
        }
    }
    return speedRecord.toFloat()
}

