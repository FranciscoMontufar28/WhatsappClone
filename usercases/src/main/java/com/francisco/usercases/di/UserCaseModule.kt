package com.francisco.usercases.di

import com.francisco.domain.AuthenticatePhoneNumberRepository
import com.francisco.domain.FireStoreRepository
import com.francisco.usercases.*
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
            ),
            GetAuthCurrentUser(
                repository
            )
        )

    @Provides
    fun provideFireStoreCloudUserCases(fireStoreRepository: FireStoreRepository) =
        FireStoreCloudUserCases(
            SaveAuthCurrentUser(
                fireStoreRepository
            ),
            UpdateAuthCurrentUser(
                fireStoreRepository
            )
        )
}