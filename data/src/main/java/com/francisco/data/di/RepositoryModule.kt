package com.francisco.data.di

import com.francisco.data.AuthenticatePhoneNumberRepositoryImpl
import com.francisco.data.FireBaseAuthenticationDataSource
import com.francisco.data.FireStoreCloudDataSource
import com.francisco.data.FireStoreRepositoryImpl
import com.francisco.domain.AuthenticatePhoneNumberRepository
import com.francisco.domain.FireStoreRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideAuthenticatePhoneNumberRepository(
        fireBaseAuthenticationDataSource: FireBaseAuthenticationDataSource
    ): AuthenticatePhoneNumberRepository =
        AuthenticatePhoneNumberRepositoryImpl(fireBaseAuthenticationDataSource)

    @Provides
    fun provideFireStoreRepository(
        fireStoreCloudDataSource: FireStoreCloudDataSource
    ): FireStoreRepository = FireStoreRepositoryImpl(fireStoreCloudDataSource)
}