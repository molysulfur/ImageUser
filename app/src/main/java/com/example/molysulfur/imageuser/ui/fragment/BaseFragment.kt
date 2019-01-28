package com.example.molysulfur.imageuser.ui.fragment

import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.Fragment
import com.example.molysulfur.imageuser.di.Injectable
import com.example.molysulfur.imageuser.idlingresource.SimpleCountingIdlingResource
import javax.inject.Inject

open class BaseFragment : Fragment(),Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mIdlingResource: SimpleCountingIdlingResource
}