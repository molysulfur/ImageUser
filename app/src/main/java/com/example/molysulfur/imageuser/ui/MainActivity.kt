package com.example.molysulfur.imageuser.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.molysulfur.imageuser.R
import com.example.molysulfur.imageuser.adapter.UserListAdapter
import com.example.molysulfur.imageuser.adapter.creator.UserCreator
import com.example.molysulfur.imageuser.adapter.item.BaseItem
import com.example.molysulfur.imageuser.data.Users
import com.example.molysulfur.imageuser.viewmodel.UserListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var userListViewModel: UserListViewModel

    private val userObserver = Observer<Users> {
        val userItemList =
            UserCreator.toUsersBaseItem(it?.data)
        setViews(userItemList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        userListViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserListViewModel::class.java)
        userListViewModel.getUserList().observe(this, userObserver)
    }

    private fun setViews(userItemList: List<BaseItem>) {
        recycler_main.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = UserListAdapter(userItemList, null,mIdlingResource)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Glide.get(this).clearMemory()
        userListViewModel.dispose()
    }

    fun getIdlingResourceInTest() = mIdlingResource

}
