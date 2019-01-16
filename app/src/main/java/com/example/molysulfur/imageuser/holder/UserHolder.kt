package com.example.molysulfur.imageuser.holder

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.molysulfur.imageuser.R
import com.example.molysulfur.imageuser.UserInfoActivity
import com.example.molysulfur.imageuser.item.UserItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_user_list.*

class UserHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),LayoutContainer{
    fun onBind(userItem: UserItem?) {
        if (userItem != null){
            Glide.with(containerView.context)
                .load(userItem.url)
                .apply(
                    RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_large))
                .into(imgUserList)
            imgUserList.setOnClickListener {
                val intent = Intent(containerView.context,UserInfoActivity::class.java)
                intent.putExtra("name",userItem.name)
                intent.putExtra("url",userItem.url)
                containerView.context.startActivity(intent)
            }
        }
    }
}