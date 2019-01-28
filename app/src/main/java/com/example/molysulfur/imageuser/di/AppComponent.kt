package com.example.molysulfur.imageuser.di

import android.app.Application
import com.example.molysulfur.imageuser.UserListApplication
import com.example.molysulfur.imageuser.di.module.ActivityModule
import com.example.molysulfur.imageuser.di.module.IdlingResourceModule
import com.example.molysulfur.imageuser.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        IdlingResourceModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(userListApplication: UserListApplication)
}