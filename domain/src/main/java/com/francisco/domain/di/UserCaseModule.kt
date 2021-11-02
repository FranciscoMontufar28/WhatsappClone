package com.francisco.domain.di

import com.francisco.domain.FireBaseStorageRepository
import com.francisco.domain.FireStoreDatabaseRepository
import com.francisco.domain.FirebaseAuthenticationRepository
import com.francisco.domain.SharedPreferencesRepository
import com.francisco.domain.usercases.*
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideAuthenticationUserCases(firebaseAuthenticationRepository: FirebaseAuthenticationRepository) =
        AuthenticationUserCases(
            AuthenticatePhoneNumber(
                firebaseAuthenticationRepository
            ),
            SignInWithPhoneNumber(
                firebaseAuthenticationRepository
            ),
            GetAuthCurrentUser(
                firebaseAuthenticationRepository
            ),
            SignOutUserAuth(
                firebaseAuthenticationRepository
            )
        )

    @Provides
    fun provideFireStoreCloudUserCases(fireStoreDatabaseRepository: FireStoreDatabaseRepository) =
        FireStoreDatabaseUserCases(
            SaveAuthCurrentUser(
                fireStoreDatabaseRepository
            ),
            UpdateAuthCurrentUser(
                fireStoreDatabaseRepository
            ),
            ValidateIfUserExist(
                fireStoreDatabaseRepository
            ),
            GetUserInformation(
                fireStoreDatabaseRepository
            ),
            DeleteProfileImage(
                fireStoreDatabaseRepository
            )
        )

    @Provides
    fun provideFireBaseStorageUserCases(fireBaseStorageRepository: FireBaseStorageRepository) =
        FireBaseStorageUserCases(
            SaveImageInCloudStore(
                fireBaseStorageRepository
            ),
            GetImageUrl(
                fireBaseStorageRepository
            ),
            DeleteImageUrlFromStorage(
                fireBaseStorageRepository
            )
        )

    @Provides
    fun provideSharedPreferencesUserCases(sharedPreferencesRepository: SharedPreferencesRepository) =
        SharedPreferencesUserCases(
            GetUserLoginAuthStatus(
                sharedPreferencesRepository
            ),
            SetUserLoginAuthStatus(
                sharedPreferencesRepository
            )
        )
}