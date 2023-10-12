package com.pallaw.swipeandlearnf

import android.app.Application
import com.pallaw.swipeandlearnf.di.appModule
import com.pallaw.swipeandlearnf.di.viewModelModule
import org.koin.core.context.GlobalContext.startKoin

class app:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            modules(
                appModule,
                viewModelModule
            )
        }
    }
}