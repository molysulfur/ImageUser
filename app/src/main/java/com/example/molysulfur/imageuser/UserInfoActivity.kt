package com.example.molysulfur.imageuser

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.molysulfur.imageuser.adapter.UserListAdapter
import com.example.molysulfur.imageuser.data.UserInfos
import com.example.molysulfur.imageuser.item.BaseItem
import com.example.molysulfur.imageuser.service.UserService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_userinfo.*

class UserInfoActivity : AppCompatActivity(){


    private var name =""
    private var url = ""
    private var listUserInfo : UserInfos? = null

    private val selectorListener = object : UserListAdapter.SelectorListener{
        override fun onCurrentImageChange(url: String,callback : UserListAdapter.SelectorListener?) {
            Glide
                .with(this@UserInfoActivity)
                .load(url)
                .apply(RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_large))
                .into(currentImageUserInfo)
            callback?.onCurrentImageChange(url,null)
        }
    }
    private var userInfoObserver = object : Observer<UserInfos>{
        override fun onComplete() {
            val userItemList = UserCreator.toUserInfoBaseItem(listUserInfo?.data)
            setViews(userItemList)
        }

        override fun onSubscribe(d: Disposable) {
        }

        override fun onNext(userInfos: UserInfos) {
            listUserInfo = userInfos
            Log.e("userInfos",userInfos.data.toString())
        }

        override fun onError(e: Throwable) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userinfo)
        if (savedInstanceState == null) init()
    }

    private fun init() {
        name = intent.getStringExtra("name")
        url = intent.getStringExtra("url")

        UserService.getRetrofit().getUserInfo(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(userInfoObserver)
    }

    private fun setViews(userItemList: List<BaseItem>) {
        Glide.with(this@UserInfoActivity)
            .load(url)
            .apply(RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder_large))
            .into(currentImageUserInfo)
        recycler_thumbnail.apply {
            layoutManager = LinearLayoutManager(this@UserInfoActivity,LinearLayoutManager.HORIZONTAL,false)
            adapter = UserListAdapter(userItemList,selectorListener)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null){
            listUserInfo = savedInstanceState.getParcelable("listUserInfo")
            url = savedInstanceState.getString("url")?:""
            val userItemList = UserCreator.toUserInfoBaseItem(listUserInfo?.data)
            setViews(userItemList)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("listUserInfo",listUserInfo)
        outState?.putString("url",url)
    }
}