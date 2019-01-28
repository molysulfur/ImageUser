package com.example.molysulfur.imageuser.di.module

import com.example.molysulfur.imageuser.idlingresource.SimpleCountingIdlingResource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class IdlingResourceModule {

    @Singleton
    @Provides
    fun provideIdlingResource() = SimpleCountingIdlingResource("GLOBAL")
}