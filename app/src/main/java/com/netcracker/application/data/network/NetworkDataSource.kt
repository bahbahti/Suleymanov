package com.netcracker.application.data.network

import androidx.lifecycle.LiveData
import com.netcracker.application.data.entity.Hardware

interface NetworkDataSource {
    val downloadedHardware: LiveData<Hardware>

    suspend fun fetchHardware(
        id: String
    )
}