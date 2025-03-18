package com.example.timersimpleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.ui.tooling.preview.Preview  // Menambahkan impor yang hilang

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimerSimpleApp()
        }
    }
}

@Composable
fun TimerSimpleApp() {
    var timers by remember { mutableStateOf(mutableListOf<Pair<Int, Boolean>>()) }
    var inputTime by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF000428), Color(0xFF004E92))))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputTime,
            onValueChange = { inputTime = it },
            label = { Text("Set Timer (seconds)", color = Color.White) },
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val timeValue = inputTime.toIntOrNull() ?: 0
                if (timeValue > 0) {
                    timers = timers.toMutableList().apply { add(Pair(timeValue, true)) } // FIX MutableList error
                }
            },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Set Timer")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            items(timers.size) { index ->
                TimerItem(timers[index].first)
            }
        }
    }
}

@Composable
fun TimerItem(initialTime: Int) {
    var time by remember { mutableStateOf(initialTime) }
    var isRunning by remember { mutableStateOf(true) }

    LaunchedEffect(isRunning) {
        while (isRunning && time > 0) {
            delay(1000L)
            time--
            if (time == 0) {
                isRunning = false
            }
        }
    }

    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "$time s", fontSize = 24.sp, color = Color.White)
        Row {
            Button(onClick = { isRunning = true }) { Text("Start") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { isRunning = false }) { Text("Stop") }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { time = initialTime; isRunning = false }) { Text("Reset") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerSimpleAppPreview() {
    TimerSimpleApp()
}
