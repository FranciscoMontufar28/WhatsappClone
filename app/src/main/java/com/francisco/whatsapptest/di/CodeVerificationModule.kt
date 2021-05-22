package com.francisco.whatsapptest.di

import com.francisco.usercases.AuthenticationUserCases
import com.francisco.whatsapptest.presentation.CodeVerificationViewModel
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class CodeVerificationModule {

    @Provides
    fun provideCodeVerificationViewModel(userCases: AuthenticationUserCases) =
        CodeVerificationViewModel(userCases)
}

@Subcomponent(modules = [CodeVerificationModule::class])
interface CodeVerificationComponent {
    val codeVerificationViewModel: CodeVerificationViewModel
}