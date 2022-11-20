package com.delab.countdowncompose

import android.app.Application
import com.gdg.stateflowz.TimerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class App: Application() {

    val appModule = module {
        viewModel {
            TimerViewModel()
        }
    }

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }
}