package com.francisco.whatsapptest.di

import com.francisco.usercases.AuthenticationUserCases
import com.francisco.usercases.FireBaseStorageUserCases
import com.francisco.usercases.FireStoreCloudUserCases
import com.francisco.usercases.SharedPreferencesUserCases
import com.francisco.whatsapptest.presentation.CompleteInformationViewModel
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class CompleteInformationModule {

    @Provides
    fun provideCompleteInformationViewModel(
        authenticationUserCases: AuthenticationUserCases,
        fireStoreCloudUserCases: FireStoreCloudUserCases,
        fireBaseStorageUserCases: FireBaseStorageUserCases,
        sharedPreferencesUserCases: SharedPreferencesUserCases
    ) = CompleteInformationViewModel(
        authenticationUserCases,
        fireStoreCloudUserCases,
        fireBaseStorageUserCases,
        sharedPreferencesUserCases
    )
}

@Subcomponent(modules = [CompleteInformationModule::class])
interface CompleteInformationComponent {
    val completeInformationViewModel: CompleteInformationViewModel
}