package com.francisco.framework.di

import com.francisco.data.FireBaseAuthenticationDataSource
import com.francisco.data.FireBaseStorageDataSource
import com.francisco.data.FireStoreCloudDataSource
import com.francisco.framework.providers.AuthProvider
import com.francisco.framework.requestparameters.FireBaseAuthenticationParameters
import com.francisco.framework.implementations.FireBaseAuthenticationDataSourceImpl
import com.francisco.framework.implementations.FireBaseStorageDataSourceImpl
import com.francisco.framework.implementations.FireStoreCloudDataSourceImpl
import com.francisco.framework.providers.ImageProvider
import com.francisco.framework.providers.UserProvider
import com.francisco.framework.requestparameters.FireStorageParameters
import com.francisco.framework.requestparameters.FireStoreCloudParameters
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
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
    ) =
        FireBaseAuthenticationParameters(
            timeStamp,
            timeUnit,
            firebaseAuth
        )

    @Provides
    @Singleton
    @Named("fireStoreCloud")
    fun provideFireStoreCloud() = FirebaseFirestore.getInstance().collection("Users")


    @Provides
    fun provideFireStoreCloudParameters(
        @Named("fireStoreCloud") collection: CollectionReference
    ) = FireStoreCloudParameters(
        collection
    )

    @Provides
    fun provideAuthProvider(fireBaseAuthenticationParameters: FireBaseAuthenticationParameters) =
        AuthProvider(
            fireBaseAuthenticationParameters
        )

    @Provides
    fun provideUserProvider(fireStoreCloudParameters: FireStoreCloudParameters) =
        UserProvider(
            fireStoreCloudParameters
        )

    @Provides
    fun provideFireBaseAuthenticationDataSource(
        authProvider: AuthProvider
    ): FireBaseAuthenticationDataSource =
        FireBaseAuthenticationDataSourceImpl(
            authProvider
        )

    @Provides
    fun provideFireStoreCloudDataSource(userProvider: UserProvider): FireStoreCloudDataSource =
        FireStoreCloudDataSourceImpl(userProvider)

    @Provides
    fun provideFireStorageParameters() = FireStorageParameters()

    @Provides
    fun provideImageProvider(fireStorageParameters: FireStorageParameters) =
        ImageProvider(fireStorageParameters)

    @Provides
    fun provideFireBaseStorageDataSource(imageProvider: ImageProvider): FireBaseStorageDataSource =
        FireBaseStorageDataSourceImpl(imageProvider)
}