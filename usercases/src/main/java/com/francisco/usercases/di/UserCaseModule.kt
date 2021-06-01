package com.francisco.usercases.di

import com.francisco.domain.FirebaseAuthenticationRepository
import com.francisco.domain.FireBaseStorageRepository
import com.francisco.domain.FireStoreDatabaseRepository
import com.francisco.domain.SharedPreferencesRepository
import com.francisco.usercases.*
import dagger.Module
import dagger.Provides

@Module
class UserCaseModule {

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