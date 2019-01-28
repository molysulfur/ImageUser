package com.example.molysulfur.imageuser.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.net.Uri
import com.example.molysulfur.imageuser.data.UserInfos
import com.example.molysulfur.imageuser.idlingresource.SimpleCountingIdlingResource
import com.example.molysulfur.imageuser.service.UserService
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserInfoViewModel @Inject constructor(private var mIdlingResource: SimpleCountingIdlingResource) : ViewModel() {

    private val userAgent = "TestVideo"
    private val BANDWIDTH_METER = DefaultBandwidthMeter()

    private var userInfoList: MutableLiveData<UserInfos>? = null
    private var userInfoObserver = object : DisposableObserver<UserInfos>() {
        override fun onComplete() {
        }

        override fun onNext(userInfos: UserInfos) {
            userInfoList?.value = userInfos
            mIdlingResource.decrement()
        }

        override fun onError(e: Throwable) {
        }
    }

    fun getUserInfo(name: String): MutableLiveData<UserInfos> {
        if (userInfoList == null) {
            mIdlingResource.increment()
            userInfoList = MutableLiveData()
            UserService.getRetrofit().getUserInfo(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userInfoObserver)
        }
        return userInfoList as MutableLiveData<UserInfos>
    }

    fun buildMediaSource(uri: Uri): MediaSource {
        return if (uri.lastPathSegment.contains("mp3") || uri.lastPathSegment.contains("mp4")) {
            ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
        } else if (uri.lastPathSegment.contains("m3u8")) {
            HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)
        } else {
            val dashChunkSourceFactory = DefaultDashChunkSource.Factory(
                DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER)
            )
            val manifestDataSourceFactory = DefaultHttpDataSourceFactory(userAgent)
            DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory).createMediaSource(uri)
        }
    }

    fun dispose() {
        userInfoObserver.dispose()
    }
}