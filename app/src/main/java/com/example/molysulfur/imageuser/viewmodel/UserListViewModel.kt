package com.example.molysulfur.imageuser.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.molysulfur.imageuser.data.Users
import com.example.molysulfur.imageuser.idlingresource.EspressoIdlingResource
import com.example.molysulfur.imageuser.service.UserService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserListViewModel @Inject constructor() : ViewModel(){

    private var userList : MutableLiveData<Users>? = null

    private val userListObserver = object : DisposableObserver<Users>() {
        override fun onComplete() {
        }

        override fun onNext(users: Users) {
            userList?.value = users
            EspressoIdlingResource.decrement()
        }

        override fun onError(e: Throwable) {
        }
    }

    fun getUserList() : LiveData<Users> {
        if (userList == null){
            EspressoIdlingResource.increment()
            userList = MutableLiveData()
            UserService.getRetrofit().getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userListObserver)
        }
        return userList as MutableLiveData<Users>
    }

    fun dispose(){
        userListObserver.dispose()
    }

}