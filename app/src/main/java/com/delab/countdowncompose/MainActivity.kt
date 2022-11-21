package com.delab.countdowncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.delab.countdowncompose.ui.theme.CountComposeTheme
import com.delab.countdowncompose.utils.TimerStates
import com.delab.countdowncompose.viewmodel.TimerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CountComposeTheme {
//                val viewModel: TimerViewModel = getViewModel()
                val viewModel: TimerViewModel by viewModel()

                // For knowing the current state of the timer
                val timerState by viewModel.timerState.collectAsState()

                // For knowing the current remaining time in String
                val time: String by viewModel.remainingTimeInString.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyTimer(timerState, time, viewModel)
                }
            }
        }
    }
}

@Composable
fun MyTimer(timerState: TimerStates, time: String, viewModel: TimerViewModel) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(text = time, textAlign = TextAlign.Center)

        Row(modifier = Modifier.fillMaxWidth(1f)) {
            OutlinedButton(
                onClick = {

                    when (timerState) {
                        is TimerStates.RUNNING -> viewModel.pauseTimer()
                        is TimerStates.PAUSED -> viewModel.resumeTimer()
                        else -> viewModel.startTimer()
                    }
                }, modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 32.dp)
                    .weight(0.5f, true)
            ) {
                Text(text = if (timerState == TimerStates.RUNNING) "Pause" else "Start")
            }

            OutlinedButton(
                onClick = { viewModel.stopTimer() }, modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 32.dp)
                    .weight(0.5f, true)
            ) {
                Text(text = "Stop")
            }
        }
    }
}
