package com.francisco.whatsapptest.di

import com.francisco.usercases.SharedPreferencesUserCases
import com.francisco.whatsapptest.presentation.OnBoardingViewModel
import dagger.Module
import dagger.Subcomponent

@Module
class OnBoardingModule {

    fun provideOnBoardingViewModel(sharedPreferencesUserCases: SharedPreferencesUserCases) =
        OnBoardingViewModel(sharedPreferencesUserCases)
}

@Subcomponent(modules = [OnBoardingModule::class])
interface OnBoardingComponent {
    val onBoardingViewModel: OnBoardingViewModel
}