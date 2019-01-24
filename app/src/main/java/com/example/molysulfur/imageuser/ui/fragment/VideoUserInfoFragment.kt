package com.example.molysulfur.imageuser.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.molysulfur.imageuser.R

class VideoUserInfoFragment : Fragment() {

    private var url = ""

    companion object {
        fun newInstance(url: String): VideoUserInfoFragment {
            val imageUserInfoFragment = VideoUserInfoFragment()
            val args = Bundle()
            args.putString("url", url)
            imageUserInfoFragment.arguments = args
            return imageUserInfoFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_userinfo_video, container, false)
        initInstances(rootView)
        return rootView
    }

    private fun initInstances(rootView: View) {
        url = arguments?.getString("url", "") ?: ""
    }
}