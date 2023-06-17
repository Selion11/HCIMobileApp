package com.example.hci_mobileapp.data.network

import com.example.hci_mobileapp.data.network.model.AllDevices
import com.example.hci_mobileapp.data.network.model.Playlist
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService{
    @GET("/api/devices")
    suspend fun getAllDevices() : Response<AllDevices>

  /*  @GET("/devices/events")
    suspend fun getAllEvents() : Response<>

    @GET("/devices")
    suspend fun getDevice(@Query("deviceId")deviceId: String) : Response<>
*/
    @POST("/api/devices/{deviceID}/{actionName}")
    suspend fun doActionBool(
      @Path("actionName") actionName: String,
      @Path("deviceID") deviceID: String,
  ) : Response<Boolean>

    @POST("/api/devices/{deviceID}/{actionName}")
    suspend fun speakerPLaylistGet(
        @Path("actionName") actionName: String,
        @Path("deviceID") deviceID: String,
    ) : Response<Playlist>
}