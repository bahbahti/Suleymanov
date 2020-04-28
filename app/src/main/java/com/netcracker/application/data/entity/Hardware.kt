package com.netcracker.application.data.entity

data class Hardware(
    val hardwareStatus: HardwareStatus,
    val id: Int,
    val name: String,
    val serial: String
)