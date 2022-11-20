package com.gdg.stateflowz

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TimerViewModel: ViewModel() {

    companion object{
        const val TOTAL_TIME = 60000L
        const val SECOND = 1000L
        const val TICK = 1L
    }

    private var _timerState = MutableStateFlow<TimerStates>(TimerStates.NOT_STARTED)
    var timerState = _timerState.asStateFlow()
    
    private var _remainingTime = MutableStateFlow<Long>(TOTAL_TIME)
    var remainingTime = _remainingTime.asStateFlow()

    private var _remainingTimeInString = MutableStateFlow<String>("Countdown Timer")
    var remainingTimeInString = _remainingTimeInString.asStateFlow()

    private lateinit var timer: CountDownTimer


    init {
        timer = createTimer(TOTAL_TIME)
    }

    fun startTimer(){
        Log.d("SAMAA", "START")
        _timerState.value = TimerStates.RUNNING
        timer.start()
    }

    fun pauseTimer(){
        _timerState.value = TimerStates.PAUSED
        timer.cancel()
    }

    fun resumeTimer(){
        _timerState.value = TimerStates.RUNNING
        timer = createTimer(_remainingTime.value)
        startTimer()
    }

    fun stopTimer(){
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
            Log.d("SAMAA", remainingTimeInString.value)

        }

        override fun onFinish() {
            _timerState.value = TimerStates.FINISHED
            _remainingTimeInString.value = "Finished"
        }
    }

    sealed class TimerStates{
        object NOT_STARTED: TimerStates()
        object RUNNING: TimerStates()
        object PAUSED: TimerStates()
        object STOPPED: TimerStates()
        object FINISHED: TimerStates()
    }
}