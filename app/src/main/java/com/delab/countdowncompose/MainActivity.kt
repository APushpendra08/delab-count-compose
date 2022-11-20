package com.delab.countdowncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.delab.countdowncompose.ui.theme.CountComposeTheme
import com.gdg.stateflowz.TimerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val viewModel by viewModels<TimerViewModel>()

        setContent {

            CountComposeTheme {
                // A surface container using the 'background' color from the theme

//                val viewModel: TimerViewModel = getViewModel()
                val viewModel: TimerViewModel by viewModel()

                val timerState by viewModel.timerState.collectAsState()
                val time: String by viewModel.remainingTimeInString.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {


                        Text(text = time, textAlign = TextAlign.Center)

                        Row(modifier = Modifier.fillMaxWidth(1f)) {
                            OutlinedButton(
                                onClick = {

                                    when (timerState) {
                                        is TimerViewModel.TimerStates.RUNNING -> viewModel.pauseTimer()
                                        is TimerViewModel.TimerStates.PAUSED -> viewModel.resumeTimer()
                                        else -> viewModel.startTimer()
                                    }
                                }, modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 32.dp)
                                    .weight(0.5f, true)
                            ) {
                                Text(text = if (timerState == TimerViewModel.TimerStates.RUNNING) "Pause" else "Start")
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
            }
        }
    }
}


//@Composable
//fun MyTimerText(viewModel: TimerViewModel) {
//    Text(
//        text = time
//    )
//}