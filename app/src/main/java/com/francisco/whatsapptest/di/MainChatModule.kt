package com.francisco.whatsapptest.di

import com.francisco.domain.usercases.AuthenticationUserCases
import com.francisco.domain.usercases.SharedPreferencesUserCases
import com.francisco.whatsapptest.presentation.MainChatViewModel
import dagger.Module
import dagger.Subcomponent

@Module
class MainChatModule {
    fun provideMainChatViewModel(
        authenticationUserCases: AuthenticationUserCases,
        sharedPreferencesUserCases: SharedPreferencesUserCases
    ) =
        MainChatViewModel(authenticationUserCases, sharedPreferencesUserCases)
}

@Subcomponent(modules = [MainChatModule::class])
interface MainChatComponent {
    val mainChatViewModel: MainChatViewModel
}