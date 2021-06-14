package com.francisco.whatsapptest.di

import com.francisco.domain.usercases.AuthenticationUserCases
import com.francisco.domain.usercases.FireBaseStorageUserCases
import com.francisco.domain.usercases.FireStoreDatabaseUserCases
import com.francisco.domain.usercases.SharedPreferencesUserCases
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