package com.example.molysulfur.imageuser.ui.fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.molysulfur.imageuser.R
import com.example.molysulfur.imageuser.viewmodel.UserInfoViewModel
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_userinfo_video.*
import kotlinx.android.synthetic.main.fragment_userinfo_video.view.*

class VideoUserInfoFragment : BaseFragment() {

    private lateinit var userInfoViewModel: UserInfoViewModel

    private var url = ""
    private var player: SimpleExoPlayer? = null
    private var playbackPosition: Long = 0
    private var currentWindow: Int = 0
    private var playWhenReady: Boolean = false

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
        playWhenReady = true
        rootView.playerUserInfo.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL)
        userInfoViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserInfoViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer(url)
        }
    }

    override fun onResume() {
        super.onResume()
        if ((Util.SDK_INT <= 23)) {
            initializePlayer(url)
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private fun initializePlayer(url: String) {
        mIdlingResource.increment()
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(context),
                DefaultTrackSelector(),
                DefaultLoadControl()
            )
            playerUserInfo.player = player
            player?.playWhenReady = playWhenReady
            player?.seekTo(currentWindow, playbackPosition)
        }
        val mediaSource = userInfoViewModel.buildMediaSource(Uri.parse(url))
        player?.prepare(mediaSource, true, false)
        player?.addListener(object : Player.DefaultEventListener() {
            override fun onLoadingChanged(isLoading: Boolean) {
                super.onLoadingChanged(isLoading)
                if (!isLoading) {
                    mIdlingResource.decrement()
                }
            }
        })
    }

    private fun releasePlayer() {
        if (player != null) {
            playbackPosition = player?.currentPosition ?: 0
            currentWindow = player?.currentWindowIndex ?: 0
            playWhenReady = player?.playWhenReady ?: true
            player?.release()
            player = null
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUiFullScreen() {
        playerUserInfo.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        playerUserInfo.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT)
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        playerUserInfo.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        playerUserInfo.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val currentOrientation = resources.configuration.orientation
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUiFullScreen()
        } else {
            hideSystemUi()
        }
    }


}