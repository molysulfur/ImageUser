package com.example.molysulfur.imageuser.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.molysulfur.imageuser.R
import com.example.molysulfur.imageuser.adapter.UserListAdapter
import com.example.molysulfur.imageuser.adapter.creator.UserCreator
import com.example.molysulfur.imageuser.adapter.item.BaseItem
import com.example.molysulfur.imageuser.data.UserInfos
import com.example.molysulfur.imageuser.idlingresource.EspressoIdlingResource
import com.example.molysulfur.imageuser.viewmodel.UserInfoViewModel
import kotlinx.android.synthetic.main.activity_userinfo.*

class UserInfoActivity : BaseActivity(),
    UserListAdapter.SelectorListener {

    private lateinit var userInfoViewModel: UserInfoViewModel
    private val userObserver = Observer<UserInfos> {
        val userItemList =
            UserCreator.toUserInfoBaseItem(it?.data)
        setViews(userItemList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userinfo)
        init()
    }

    private fun init() {
        userInfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserInfoViewModel::class.java)
        Glide.get(this).setMemoryCategory(MemoryCategory.LOW)
        userInfoViewModel.getUserInfo(intent.getStringExtra("name"))
            .observe(this,userObserver)
    }

    private fun setViews(userItemList: List<BaseItem>) {
        EspressoIdlingResource.increment()
        loadImageWithGlide(intent.getStringExtra("url"))
        recycler_thumbnail.apply {
            layoutManager = LinearLayoutManager(this@UserInfoActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = UserListAdapter(userItemList, this@UserInfoActivity)
        }
    }

    private fun loadImageWithGlide(url: String) {
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
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    e?.printStackTrace()
                    Toast.makeText(this@UserInfoActivity,"Load is Failed",Toast.LENGTH_SHORT).show()
                    EspressoIdlingResource.decrement()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    EspressoIdlingResource.decrement()
                    return false
                }
            })
            .into(currentImageUserInfo)
    }

    override fun onDestroy() {
        super.onDestroy()
        userInfoViewModel.dispose()
        recycler_thumbnail.adapter = null
        Glide.get(this).clearMemory()
    }

    override fun onCurrentImageChange(url: String, callback: UserListAdapter.SelectorListener?) {
        EspressoIdlingResource.increment()
        loadImageWithGlide(url)
        callback?.onCurrentImageChange(url, null)
    }
}