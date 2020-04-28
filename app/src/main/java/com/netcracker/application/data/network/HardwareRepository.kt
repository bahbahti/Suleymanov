package com.netcracker.application.data.network

import com.netcracker.application.data.entity.ContentResponse
import com.netcracker.application.data.entity.Hardware

class HardwareRepository(val apiHardwareService: ApiHardwareService) {

    fun getHardwareList(): io.reactivex.Observable<ContentResponse> {
        return apiHardwareService.getHardwareList()
    }

    fun getHardware(id : String): io.reactivex.Observable<Hardware> {
        return apiHardwareService.getHardware(id)
    }
}