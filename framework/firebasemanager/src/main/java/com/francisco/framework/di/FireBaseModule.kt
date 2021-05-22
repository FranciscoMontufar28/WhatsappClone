package com.francisco.framework.di

import com.francisco.data.FireBaseAuthenticationDataSource
import com.francisco.framework.AuthProvider
import com.francisco.framework.BaseRequestParameters
import com.francisco.framework.FireBaseAuthenticationDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class FireBaseModule {

    @Provides
    @Singleton
    @Named("timeUnit")
    fun provideTimeUnit() = TimeUnit.SECONDS

    @Provides
    @Singleton
    @Named("timeStamp")
    fun provideTimeStamp() = 60L

    @Provides
    @Singleton
    @Named("firebaseAuth")
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    fun provideBaseRequestParameters(
        @Named("timeStamp") timeStamp: Long,
        @Named("timeUnit") timeUnit: TimeUnit,
        @Named("firebaseAuth") firebaseAuth: FirebaseAuth
    ) = BaseRequestParameters(
        timeStamp,
        timeUnit,
        firebaseAuth
    )

    @Provides
    fun provideAuthProvider(baseRequestParameters: BaseRequestParameters) =
        AuthProvider(baseRequestParameters)

    @Provides
    fun provideFireBaseAuthenticationDataSource(
        authProvider: AuthProvider
    ): FireBaseAuthenticationDataSource =
        FireBaseAuthenticationDataSourceImpl(authProvider)
}