package com.francisco.data.di

import com.francisco.data.AuthenticatePhoneNumberRepositoryImpl
import com.francisco.data.FireBaseAuthenticationDataSource
import com.francisco.domain.AuthenticatePhoneNumberRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideAuthenticatePhoneNumberRepository(
        fireBaseAuthenticationDataSource: FireBaseAuthenticationDataSource
    ): AuthenticatePhoneNumberRepository =
        AuthenticatePhoneNumberRepositoryImpl(fireBaseAuthenticationDataSource)
}