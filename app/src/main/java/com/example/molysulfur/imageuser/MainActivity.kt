package com.example.molysulfur.imageuser

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.molysulfur.imageuser.adapter.UserListAdapter
import com.example.molysulfur.imageuser.data.Users
import com.example.molysulfur.imageuser.item.BaseItem
import com.example.molysulfur.imageuser.service.UserService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var userlists : Users? = null
    private val userListObserver = object : Observer<Users> {
        override fun onComplete() {
            val userItemList = UserCreator.toBaseItem(userlists?.data)
            setViews(userItemList)
        }

        override fun onSubscribe(d: Disposable) {
        }

        override fun onNext(users: Users) {
            userlists = users
        }

        override fun onError(e: Throwable) {
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null){
            userlists = savedInstanceState.getParcelable("userItemList")
            val userItemList = UserCreator.toBaseItem(userlists?.data)
            setViews(userItemList)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("userItemList",userlists)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            init()
        }
    }

    private fun init() {
        UserService.getRetrofit()
            .getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(userListObserver)
    }

    private fun setViews(userItemList: List<BaseItem>) {
        recycler_main.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = UserListAdapter(userItemList)
        }
    }
}
