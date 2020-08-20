package com.anonlatte.trends.di

import android.app.Application
import com.anonlatte.trends.TrendsApp
import com.anonlatte.trends.di.module.AppModule
import com.anonlatte.trends.di.module.DatabaseModule
import com.anonlatte.trends.di.module.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent {

    fun inject(trendsApp: TrendsApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}