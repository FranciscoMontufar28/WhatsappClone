package com.francisco.whatsapptest.di

import android.app.Application
import com.francisco.data.di.RepositoryModule
import com.francisco.domain.di.DomainModule
import com.francisco.framework.di.FireBaseModule
import com.francisco.sharedpreferences.di.SharedPreferencesModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DomainModule::class,
        RepositoryModule::class,
        FireBaseModule::class,
        SharedPreferencesModule::class]
)
interface AppComponent {

    fun inject(module: CodeVerificationModule): CodeVerificationComponent
    fun inject(module: CompleteInformationModule): CompleteInformationComponent
    fun inject(module: OnBoardingModule): OnBoardingComponent
    fun inject(module: MainChatModule): MainChatComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }
}