package com.example.molysulfur.imageuser.di.module

import com.example.molysulfur.imageuser.ui.MainActivity
import com.example.molysulfur.imageuser.ui.UserInfoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeUserInfoActivity(): UserInfoActivity

}