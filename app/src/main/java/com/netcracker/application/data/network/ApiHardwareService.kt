package com.netcracker.application.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.netcracker.application.data.entity.ContentResponse
import com.netcracker.application.data.entity.Hardware
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiHardwareService {

    @GET("hardwares/{id}")
    fun getHardware(@Path("id") id : String)
            : io.reactivex.Observable<Hardware>

    @GET("hardwares")
    fun getHardwareList()
            : io.reactivex.Observable<ContentResponse>

    companion object Factory{
        fun create(): ApiHardwareService {
        //operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApiHardwareService {

            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                //.addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://mk-nc.herokuapp.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiHardwareService::class.java)
        }
    }
}