package com.francisco.data.di

import com.francisco.data.*
import com.francisco.domain.FireBaseStorageRepository
import com.francisco.domain.FireStoreDatabaseRepository
import com.francisco.domain.FirebaseAuthenticationRepository
import com.francisco.domain.SharedPreferencesRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideFirebaseAuthenticationRepository(
        fireBaseAuthenticationDataSource: FireBaseAuthenticationDataSource
    ): FirebaseAuthenticationRepository =
        FirebaseAuthenticationRepositoryImpl(fireBaseAuthenticationDataSource)

    @Provides
    fun provideFireStoreRepository(
        fireStoreDatabaseDataSource: FireStoreDatabaseDataSource
    ): FireStoreDatabaseRepository =
        FireStoreDatabaseDatabaseRepositoryImpl(fireStoreDatabaseDataSource)

    @Provides
    fun provideFireBaseStorageRepository(
        fireBaseStorageDataSource: FireBaseStorageDataSource
    ): FireBaseStorageRepository = FireBaseStorageRepositoryImpl(fireBaseStorageDataSource)

    @Provides
    fun provideSharedPreferencesRepository(appPreferencesHelper: AppPreferencesHelper): SharedPreferencesRepository =
        SharedPreferencesRepositoryImpl(appPreferencesHelper)
}