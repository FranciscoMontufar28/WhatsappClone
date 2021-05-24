package com.francisco.whatsapptest.di

import com.francisco.usercases.AuthenticationUserCases
import com.francisco.usercases.FireBaseStorageUserCases
import com.francisco.usercases.FireStoreCloudUserCases
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
        fireBaseStorageUserCases: FireBaseStorageUserCases
    ) = CompleteInformationViewModel(
        authenticationUserCases,
        fireStoreCloudUserCases,
        fireBaseStorageUserCases
    )
}

@Subcomponent(modules = [CompleteInformationModule::class])
interface CompleteInformationComponent {
    val completeInformationViewModel: CompleteInformationViewModel
}