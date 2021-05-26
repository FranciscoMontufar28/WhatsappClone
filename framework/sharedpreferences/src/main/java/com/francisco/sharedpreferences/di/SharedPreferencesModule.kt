package com.francisco.sharedpreferences.di

import com.francisco.data.AppPreferencesHelper
import com.francisco.sharedpreferences.AppPreferencesHelperImpl
import dagger.Module
import dagger.Provides

@Module
class SharedPreferencesModule {

    @Provides
    fun provideAppPreferencesHelper(): AppPreferencesHelper = AppPreferencesHelperImpl()
}