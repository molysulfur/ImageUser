package com.example.molysulfur.imageuser.di.module

import com.example.molysulfur.imageuser.ui.fragment.ImageUserInfoFragment
import com.example.molysulfur.imageuser.ui.fragment.VideoUserInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeVideoUserInfoFragment(): VideoUserInfoFragment

    @ContributesAndroidInjector
    abstract fun contributeImageUserInfoFragment(): ImageUserInfoFragment
}