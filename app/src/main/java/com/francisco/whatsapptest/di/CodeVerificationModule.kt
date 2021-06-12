package com.francisco.whatsapptest.di

import com.francisco.domain.usercases.AuthenticationUserCases
import com.francisco.domain.usercases.FireStoreDatabaseUserCases
import com.francisco.whatsapptest.presentation.CodeVerificationViewModel
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class CodeVerificationModule {

    @Provides
    fun provideCodeVerificationViewModel(
        authenticationUserCases: AuthenticationUserCases,
        fireStoreDatabaseUserCases: FireStoreDatabaseUserCases
    ) =
        CodeVerificationViewModel(authenticationUserCases, fireStoreDatabaseUserCases)
}

@Subcomponent(modules = [CodeVerificationModule::class])
interface CodeVerificationComponent {
    val codeVerificationViewModel: CodeVerificationViewModel
}