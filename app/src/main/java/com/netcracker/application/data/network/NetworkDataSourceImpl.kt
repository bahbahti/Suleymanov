package com.netcracker.application.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.netcracker.application.data.entity.Hardware
import com.netcracker.application.exception.NoConnectivityException

class NetworkDataSourceImpl(
    private val apiHardwareService: ApiHardwareService
) : NetworkDataSource {

    private val _donwloadedHardware = MutableLiveData<Hardware>()
    override val downloadedHardware: LiveData<Hardware>
        get() = _donwloadedHardware

    override suspend fun fetchHardware(id: String) {
/*        try {
            val fetchedHardware = apiHardwareService
                .getHardware(id)
                .await()
            _donwloadedHardware.postValue(fetchedHardware)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection")
        }*/
    }
}