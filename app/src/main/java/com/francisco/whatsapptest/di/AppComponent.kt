package com.francisco.whatsapptest.di

import android.app.Application
import com.francisco.data.di.RepositoryModule
import com.francisco.framework.di.FireBaseModule
import com.francisco.sharedpreferences.di.SharedPreferencesModule
import com.francisco.usercases.di.UserCaseModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        UserCaseModule::class,
        RepositoryModule::class,
        FireBaseModule::class,
        SharedPreferencesModule::class]
)
interface AppComponent {

    fun inject(module: CodeVerificationModule): CodeVerificationComponent
    fun inject(module: CompleteInformationModule): CompleteInformationComponent
    fun inject(module: OnBoardingModule): OnBoardingComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}