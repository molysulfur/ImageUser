package com.example.molysulfur.imageuser.holder

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.molysulfur.imageuser.R
import com.example.molysulfur.imageuser.adapter.UserListAdapter
import com.example.molysulfur.imageuser.item.UserInfoItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_thumbnail.*

class UserInfoHolder(
    override val containerView: View,
    private val callback: UserListAdapter.SelectorListener
) : RecyclerView.ViewHolder(containerView), LayoutContainer{

    private lateinit var clickThumbnaiListener : UserListAdapter.SelectorListener

    fun onBind(userInfoItem: UserInfoItem?) {
        if (userInfoItem != null){
            Glide.with(containerView.context)
                .load(userInfoItem.thumbnail)
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.placeholder_small)
                        .fitCenter()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .format(DecodeFormat.PREFER_RGB_565))
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