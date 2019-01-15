package com.example.molysulfur.imageuser.service

import com.example.molysulfur.imageuser.data.Users
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users")
    fun getUsers() : Observable<Users>

    @GET("{user}")
    fun getUsers(@Path("user") userName : String) : Observable<Users>

    companion object {
        private val BASE_URL = "http://mock.nextzy.me/api/v1/"
        fun getRetrofit(): UserService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserService::class.java)
        }
    }
}