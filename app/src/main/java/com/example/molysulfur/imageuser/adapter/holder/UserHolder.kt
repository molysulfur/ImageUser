package com.example.molysulfur.imageuser.adapter.holder

import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.molysulfur.imageuser.R
import com.example.molysulfur.imageuser.adapter.item.UserItem
import com.example.molysulfur.imageuser.idlingresource.SimpleCountingIdlingResource
import com.example.molysulfur.imageuser.ui.UserInfoActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.layout_user_list.*

class UserHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun onBind(userItem: UserItem?, @Nullable mIdlingResource: SimpleCountingIdlingResource) {
        if (userItem != null) {
            mIdlingResource.increment()
            Glide.with(containerView.context)
                .load(userItem.url)
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
                        Toast.makeText(containerView.context, "Load is Failed", Toast.LENGTH_SHORT).show()
                        mIdlingResource.decrement()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        mIdlingResource.decrement()
                        return false
                    }
                })
                .into(imgUserList)
            imgUserList.setOnClickListener {
                val intent = Intent(containerView.context, UserInfoActivity::class.java)
                intent.putExtra("name", userItem.name)
                intent.putExtra("url", userItem.url)
                containerView.context.startActivity(intent)
            }
        }
    }
}