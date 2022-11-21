package com.delab.countdowncompose.utils

/**
 * This class contains the state of the Timer
 *
 * NOT_STARTED - Timer is in initial state
 * RUNNING - Timer is running
 * PAUSED - Timer is paused by user
 * STOPPED - Timer is abruptly stopped by user
 * FINISHED - Timer has finished
 *
 */
sealed class TimerStates{
    object NOT_STARTED: TimerStates()
    object RUNNING: TimerStates()
    object PAUSED: TimerStates()
    object STOPPED: TimerStates()
    object FINISHED: TimerStates()
}