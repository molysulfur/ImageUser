package com.example.molysulfur.imageuser

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.example.molysulfur.imageuser.adapter.UserListAdapter
import com.example.molysulfur.imageuser.data.Users
import com.example.molysulfur.imageuser.item.BaseItem
import com.example.molysulfur.imageuser.service.UserService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listUsers : Users? = null
    private val userListObserver = object : DisposableObserver<Users>() {
        override fun onComplete() {
            val userItemList = UserCreator.toUsersBaseItem(listUsers?.data)
            setViews(userItemList)
        }

        override fun onNext(users: Users) {
            listUsers = users
        }

        override fun onError(e: Throwable) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            init()
        }
    }

    private fun init() {
        Log.e("init","create main")
        UserService.getRetrofit()
            .getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(userListObserver)
    }

    private fun setViews(userItemList: List<BaseItem>) {
        recycler_main.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = UserListAdapter(userItemList,null)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null){
            listUsers = savedInstanceState.getParcelable("userItemList")
            val userItemList = UserCreator.toUsersBaseItem(listUsers?.data)
            setViews(userItemList)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("userItemList",listUsers)
    }

    override fun onDestroy() {
        super.onDestroy()
        userListObserver.dispose()
    }

}
