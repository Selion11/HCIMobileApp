package com.example.hci_mobileapp.data.network

import com.example.hci_mobileapp.data.network.model.AllDevices
import com.example.hci_mobileapp.data.network.model.ApiDevice
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/devices")
    suspend fun getAllDevices() : Response<AllDevices>

  /*  @GET("/devices/events")
    suspend fun getAllEvents() : Response<>

    @GET("/devices")
    suspend fun getDevice(@Query("deviceId")deviceId: String) : Response<>
*/
}