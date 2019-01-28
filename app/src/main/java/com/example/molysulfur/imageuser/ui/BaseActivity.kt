package com.example.molysulfur.imageuser.ui

import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.molysulfur.imageuser.idlingresource.SimpleCountingIdlingResource
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector{

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mIdlingResource: SimpleCountingIdlingResource

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

}