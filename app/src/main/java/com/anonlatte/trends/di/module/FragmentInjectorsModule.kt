package com.anonlatte.trends.di.module

import com.anonlatte.trends.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentInjectorsModule {

    @ContributesAndroidInjector
    abstract fun injectHomeFragment(): HomeFragment

}