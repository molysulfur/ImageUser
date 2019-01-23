package com.example.molysulfur.imageuser.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.molysulfur.imageuser.di.ViewModelKey
import com.example.molysulfur.imageuser.viewmodel.UserInfoViewModel
import com.example.molysulfur.imageuser.viewmodel.UserListViewModel
import com.example.molysulfur.imageuser.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    abstract fun bindUserListViewModel(viewModel: UserListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserInfoViewModel::class)
    abstract fun bindUserInfoViewModel(viewModel: UserInfoViewModel): ViewModel
}