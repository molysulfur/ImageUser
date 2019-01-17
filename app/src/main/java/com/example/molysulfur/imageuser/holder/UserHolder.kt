package com.example.molysulfur.imageuser.holder

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.molysulfur.imageuser.R
import com.example.molysulfur.imageuser.UserInfoActivity
import com.example.molysulfur.imageuser.item.UserItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_user_list.*

class UserHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),LayoutContainer{
    fun onBind(userItem: UserItem?) {
        if (userItem != null){
            GlideBuilder()
            Glide.with(containerView.context)
                .load(userItem.url)
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .apply(
                    RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_large)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .format(DecodeFormat.PREFER_ARGB_8888))
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