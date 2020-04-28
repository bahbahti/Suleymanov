package com.netcracker.application.data.network


//singleton
object HardwareRepositoryProvider {

    fun providehardwarerepository() : HardwareRepository {
        return HardwareRepository(ApiHardwareService.create())
    }
}