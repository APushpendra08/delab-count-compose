package com.delab.countdowncompose.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import com.delab.countdowncompose.utils.TimerStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * TimerViewModel class contains Flows for handling the Timer States as well as function for handling them
 */
class TimerViewModel: ViewModel() {

    companion object{
        const val TOTAL_TIME = 60000L
        const val SECOND = 1000L
        const val TICK = 1L
        const val TAG = "TimerViewModel"
    }

    /**
      *  This Flow will keep the state of the timer - NOT_STARTED, RUNNING, PAUSED, FINISHED, STOPPED(ABRUPT)
      */
    private var _timerState = MutableStateFlow<TimerStates>(TimerStates.NOT_STARTED)
    var timerState = _timerState.asStateFlow()

    /**
     * remainingTime will store the remaining time, mainly for the reason of continuing the timer from the same time where it was paused
     */
    private var _remainingTime = MutableStateFlow<Long>(TOTAL_TIME)
    var remainingTime = _remainingTime.asStateFlow()

    /**
     * remainingTimeInString will store the remaining time in string format, or directly emitting it to UI layer
     */
    private var _remainingTimeInString = MutableStateFlow<String>("Countdown Timer")
    var remainingTimeInString = _remainingTimeInString.asStateFlow()

    private lateinit var timer: CountDownTimer

    init {
        timer = createTimer(TOTAL_TIME)
    }

    /**
     * Methods for handling the states of the timer
     */
    fun startTimer(){
        Log.d(TAG, "START")
        _timerState.value = TimerStates.RUNNING
        timer.start()
    }

    fun pauseTimer(){
        Log.d(TAG, "PAUSE")
        _timerState.value = TimerStates.PAUSED
        timer.cancel()
    }

    fun resumeTimer(){
        Log.d(TAG, "RESUME")
        _timerState.value = TimerStates.RUNNING
        timer = createTimer(_remainingTime.value)
        startTimer()
    }

    fun stopTimer(){
        Log.d(TAG, "STOP")
        _timerState.value = TimerStates.STOPPED
        timer.cancel()
        _remainingTime.value = TOTAL_TIME
        _remainingTimeInString.value = "Countdown Timer"
        timer = createTimer(TOTAL_TIME)
    }

    fun createTimer(millis: Long) = object : CountDownTimer(millis, TICK){
        override fun onTick(p0: Long) {
            _remainingTime.value = p0

            val seconds: Int = (p0 / 1000).toInt()
            val millis: Int = (p0 % 1000).toInt()

            _remainingTimeInString.value = "$seconds s : $millis millis"
            Log.d(TAG, remainingTimeInString.value)

        }

        override fun onFinish() {
            _timerState.value = TimerStates.FINISHED
            _remainingTimeInString.value = "Finished"
        }
    }
}