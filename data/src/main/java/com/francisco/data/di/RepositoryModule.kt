package com.francisco.data.di

import com.francisco.data.*
import com.francisco.domain.AuthenticatePhoneNumberRepository
import com.francisco.domain.FireBaseStorageRepository
import com.francisco.domain.FireStoreRepository
import com.francisco.domain.SharedPreferencesRepository
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

    @Provides
    fun provideFireBaseStorageRepository(
        fireBaseStorageDataSource: FireBaseStorageDataSource
    ): FireBaseStorageRepository = FireBaseStorageRepositoryImpl(fireBaseStorageDataSource)

    @Provides
    fun provideSharedPreferencesRepository(appPreferencesHelper: AppPreferencesHelper): SharedPreferencesRepository =
        SharedPreferencesRepositoryImpl(appPreferencesHelper)
}