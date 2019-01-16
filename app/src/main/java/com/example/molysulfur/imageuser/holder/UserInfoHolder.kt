package com.example.molysulfur.imageuser.holder

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.molysulfur.imageuser.R
import com.example.molysulfur.imageuser.adapter.UserListAdapter
import com.example.molysulfur.imageuser.item.UserInfoItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_thumbnail.*

class UserInfoHolder(
    override val containerView: View,
    val callback: UserListAdapter.SelectorListener
) : RecyclerView.ViewHolder(containerView), LayoutContainer{

    private lateinit var clickThumbnaiListener : UserListAdapter.SelectorListener

    fun onBind(userInfoItem: UserInfoItem?) {
        if (userInfoItem != null){
            Glide.with(containerView.context)
                .load(userInfoItem.thumbnail)
                .apply(
                    RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.placeholder_small))
                .into(imgThumbnail)
            imgThumbnail.setOnClickListener {
                clickThumbnaiListener.onCurrentImageChange(userInfoItem.url?:"",callback)
            }
            if (userInfoItem.current){
                imgThumbnail.borderColor = Color.BLUE
            }else{
                imgThumbnail.borderColor = Color.WHITE
            }
        }
    }

    fun onCurrentChange(selectorListener: UserListAdapter.SelectorListener){
        this.clickThumbnaiListener = selectorListener
    }
}