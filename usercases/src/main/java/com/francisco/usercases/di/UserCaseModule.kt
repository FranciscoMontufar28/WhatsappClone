package com.francisco.usercases.di

import com.francisco.domain.AuthenticatePhoneNumberRepository
import com.francisco.domain.FireBaseStorageRepository
import com.francisco.domain.FireStoreRepository
import com.francisco.domain.SharedPreferencesRepository
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
            ),
            SignOutUserAuth(
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
            ),
            ValidateIfUserExist(
                fireStoreRepository
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