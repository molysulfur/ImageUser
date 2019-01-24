package com.example.molysulfur.imageuser.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.molysulfur.imageuser.idlingresource.EspressoIdlingResource
import kotlinx.android.synthetic.main.fragment_userinfo_image.view.*

class ImageUserInfoFragment : Fragment() {

    private var url = ""

    companion object {
        fun newInstance(url: String): ImageUserInfoFragment {
            val imageUserInfoFragment = ImageUserInfoFragment()
            val args = Bundle()
            args.putString("url", url)
            imageUserInfoFragment.arguments = args
            return imageUserInfoFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_userinfo_image, container, false)
        initInstances(rootView)
        return rootView
    }

    private fun initInstances(rootView: View) {
        url = arguments?.getString("url", "") ?: ""
        Glide.with(this)
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
            .listener(object: RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    e?.printStackTrace()
                    Toast.makeText(activity,"Load is Failed",Toast.LENGTH_SHORT).show()
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
            .into(rootView.imageUserInfo)
    }
}