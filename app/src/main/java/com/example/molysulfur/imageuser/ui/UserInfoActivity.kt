package com.example.molysulfur.imageuser.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.example.molysulfur.imageuser.R
import com.example.molysulfur.imageuser.adapter.UserListAdapter
import com.example.molysulfur.imageuser.adapter.creator.UserCreator
import com.example.molysulfur.imageuser.adapter.item.BaseItem
import com.example.molysulfur.imageuser.adapter.item.UserInfoItem
import com.example.molysulfur.imageuser.data.UserInfos
import com.example.molysulfur.imageuser.idlingresource.EspressoIdlingResource
import com.example.molysulfur.imageuser.ui.fragment.ImageUserInfoFragment
import com.example.molysulfur.imageuser.ui.fragment.VideoUserInfoFragment
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
        loadImageWithGlide(intent.getStringExtra("url"),(userItemList[0] as UserInfoItem).dataType)
        recycler_thumbnail.apply {
            layoutManager = LinearLayoutManager(this@UserInfoActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = UserListAdapter(userItemList, this@UserInfoActivity)
        }
    }

    private fun loadImageWithGlide(url: String, dataType: String?="") {
        Log.e("data type",dataType)
        when(dataType){
            "video" ->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container,VideoUserInfoFragment.newInstance(url))
                    .commit()
            }
            "image" ->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container,ImageUserInfoFragment.newInstance(url))
                    .commit()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        userInfoViewModel.dispose()
        recycler_thumbnail.adapter = null
        Glide.get(this).clearMemory()
    }

    override fun onCurrentImageChange(url: String, dataType: String,callback: UserListAdapter.SelectorListener?) {
        EspressoIdlingResource.increment()
        loadImageWithGlide(url,dataType)
        callback?.onCurrentImageChange(url,dataType,null)
    }
}