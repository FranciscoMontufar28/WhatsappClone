package com.francisco.whatsapptest.di

import com.francisco.usercases.AuthenticationUserCases
import com.francisco.usercases.FireBaseStorageUserCases
import com.francisco.usercases.FireStoreDatabaseUserCases
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
        fireStoreDatabaseUserCases: FireStoreDatabaseUserCases,
        fireBaseStorageUserCases: FireBaseStorageUserCases,
        sharedPreferencesUserCases: SharedPreferencesUserCases
    ) = CompleteInformationViewModel(
        authenticationUserCases,
        fireStoreDatabaseUserCases,
        fireBaseStorageUserCases,
        sharedPreferencesUserCases
    )
}

@Subcomponent(modules = [CompleteInformationModule::class])
interface CompleteInformationComponent {
    val completeInformationViewModel: CompleteInformationViewModel
}