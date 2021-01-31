package application.network

import application.data.entity.Gif
import retrofit2.Call

class Repository(private val apiService: ApiService) {

    fun getGifInfo(id: Int): Call<Gif> {
        return apiService.getGifInfo(id)
    }
}