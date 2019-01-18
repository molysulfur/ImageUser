package com.example.molysulfur.imageuser

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.molysulfur.imageuser.adapter.UserListAdapter
import com.example.molysulfur.imageuser.data.UserInfos
import com.example.molysulfur.imageuser.item.BaseItem
import com.example.molysulfur.imageuser.service.UserService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_userinfo.*

class UserInfoActivity : AppCompatActivity(),
    UserListAdapter.SelectorListener {
    override fun onCurrentImageChange(url: String, callback: UserListAdapter.SelectorListener?) {
        Glide.with(this@UserInfoActivity)
            .load(url)
            .transition(GenericTransitionOptions.with(R.anim.fade_in))
            .apply(
                RequestOptions()
                    .fitCenter()
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_large)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .format(DecodeFormat.PREFER_RGB_565)
            )
            .into(currentImageUserInfo)
        callback?.onCurrentImageChange(url, null)
    }

    private var url = ""
    private var listUserInfo: UserInfos? = null

    private var userInfoObserver = object : DisposableObserver<UserInfos>() {
        override fun onComplete() {
            val userItemList = UserCreator.toUserInfoBaseItem(listUserInfo?.data)
            setViews(userItemList)
        }

        override fun onNext(userInfos: UserInfos) {
            listUserInfo = userInfos
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
        url = intent.getStringExtra("url")

        UserService.getRetrofit().getUserInfo(intent.getStringExtra("name"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(userInfoObserver)
    }

    private fun setViews(userItemList: List<BaseItem>) {
        Glide.with(this@UserInfoActivity)
            .load(url)
            .transition(GenericTransitionOptions.with(R.anim.fade_in))
            .apply(
                RequestOptions()
                    .fitCenter()
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_large)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .format(DecodeFormat.PREFER_RGB_565)
            )
            .into(currentImageUserInfo)
        recycler_thumbnail.apply {
            layoutManager = LinearLayoutManager(this@UserInfoActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = UserListAdapter(userItemList, this@UserInfoActivity)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            listUserInfo = savedInstanceState.getParcelable("listUserInfo")
            url = savedInstanceState.getString("url") ?: ""
            val userItemList = UserCreator.toUserInfoBaseItem(listUserInfo?.data)
            setViews(userItemList)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("listUserInfo", listUserInfo)
        outState?.putString("url", url)
    }

    override fun onDestroy() {
        super.onDestroy()
        userInfoObserver.dispose()
        recycler_thumbnail.adapter = null

    }
}