package com.netcracker.application.data.network

import com.netcracker.application.data.entity.ContentResponse
import com.netcracker.application.data.entity.Hardware
import io.reactivex.Observable
import retrofit2.Response

class HardwareRepository(val apiHardwareService: ApiHardwareService) {

    fun getHardwareList(): Observable<ContentResponse> {
        return apiHardwareService.getHardwareList()
    }

    fun getHardware(id : String): Observable<Hardware> {
        return apiHardwareService.getHardware(id)
    }

    fun changeHardwareStatus(hardwareId : Int, hardwareStatusId : Int) : Observable<Response<Void>> {
        return apiHardwareService.changeHardwareStatus(hardwareId, hardwareStatusId)
    }
}