package application.network

import com.google.gson.GsonBuilder
import application.data.entity.Gif
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit


const val BASE_URL = "http://developerslife.ru/"

interface ApiService {
    @GET("{id}?json=true")
    fun getGifInfo(@Path("id") id: Int): Call<Gif>

    companion object Factory{
        fun create(): ApiService {

            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(2L, TimeUnit.MINUTES)
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()


            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)
        }
    }
}