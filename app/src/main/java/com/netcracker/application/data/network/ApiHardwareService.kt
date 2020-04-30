package com.netcracker.application.data.network

import com.netcracker.application.data.entity.ContentResponse
import com.netcracker.application.data.entity.Hardware
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiHardwareService {

    @GET("hardwares/{id}")
    fun getHardware(@Path("id") id : String)
            : io.reactivex.Observable<Hardware>

    @GET("hardwares")
    fun getHardwareList(): Observable<ContentResponse>


    ///search/setHardwareStatusByHardware_Id?hardwareId=1&hardwareStatusId=3
    @GET("hardwares/search/setHardwareStatusByHardware_Id")
    fun changeHardwareStatus (
        @Query("hardwareId") hardwareId : Int,
        @Query("hardwareStatusId") hardwareStatusId : Int
    ) : Observable<Response<Void>>

    companion object Factory{
        fun create(): ApiHardwareService {

            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
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