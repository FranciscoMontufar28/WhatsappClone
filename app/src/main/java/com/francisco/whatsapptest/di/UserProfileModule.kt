package com.francisco.whatsapptest.di

import com.francisco.domain.usercases.AuthenticationUserCases
import com.francisco.domain.usercases.FireStoreDatabaseUserCases
import com.francisco.whatsapptest.presentation.UserProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class UserProfileModule {

    fun provideUserProfileViewModel(
        fireStoreDatabaseUserCases: FireStoreDatabaseUserCases,
        authenticationUserCases: AuthenticationUserCases
    ) = UserProfileViewModel(fireStoreDatabaseUserCases, authenticationUserCases)
}

@Subcomponent(modules = [UserProfileModule::class])
interface UserProfileComponent {
    val userProfileViewModel: UserProfileViewModel
}