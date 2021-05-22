package com.francisco.usercases.di

import com.francisco.domain.AuthenticatePhoneNumberRepository
import com.francisco.usercases.AuthenticatePhoneNumber
import com.francisco.usercases.AuthenticationUserCases
import com.francisco.usercases.SignInWithPhoneNumber
import dagger.Module
import dagger.Provides

@Module
class UserCaseModule {

    @Provides
    fun provideAuthenticationUserCases(repository: AuthenticatePhoneNumberRepository) =
        AuthenticationUserCases(
            AuthenticatePhoneNumber(
                repository
            ),
            SignInWithPhoneNumber(
                repository
            )
        )
}