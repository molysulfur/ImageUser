package com.example.molysulfur.imageuser.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.molysulfur.imageuser.data.UserInfos
import com.example.molysulfur.imageuser.idlingresource.EspressoIdlingResource
import com.example.molysulfur.imageuser.service.UserService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserInfoViewModel @Inject constructor() : ViewModel(){

    private var userInfoList : MutableLiveData<UserInfos>? = null
    private var userInfoObserver = object : DisposableObserver<UserInfos>() {
        override fun onComplete() {
        }

        override fun onNext(userInfos: UserInfos) {
            userInfoList?.value = userInfos
            EspressoIdlingResource.decrement()
        }

        override fun onError(e: Throwable) {
        }
    }

    fun getUserInfo(name : String): MutableLiveData<UserInfos> {
        if (userInfoList == null){
            EspressoIdlingResource.increment()
            userInfoList = MutableLiveData()
            UserService.getRetrofit().getUserInfo(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfoObserver)
        }
        return userInfoList as MutableLiveData<UserInfos>
    }

    fun dispose(){
        userInfoObserver.dispose()
    }
}