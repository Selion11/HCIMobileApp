package com.example.hci_mobileapp.data.network

import com.example.hci_mobileapp.data.network.model.AllDevices
import com.example.hci_mobileapp.data.network.model.ApiDevice
import com.example.hci_mobileapp.data.network.model.Playlist
import com.example.hci_mobileapp.data.network.model.faucetData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{
    @GET("/api/devices")
    suspend fun getAllDevices() : Response<AllDevices>

    @GET("/api/devices/{deviceID}")
    suspend fun getDevice(
        @Path("deviceID") deviceID: String?
    ) : Response<ApiDevice>

  /*  @GET("/devices/events")
    suspend fun getAllEvents() : Response<>

    @GET("/devices")
    suspend fun getDevice(@Query("deviceId")deviceId: String) : Response<>
*/
    @PUT("/api/devices/{deviceID}/{actionName}")
    suspend fun doActionBool(
      @Path("actionName") actionName: String?,
      @Path("deviceID") deviceID: String?,
  ) : Response<Boolean>

    @PUT("/api/devices/{deviceID}/{actionName}")
    suspend fun speakerPLaylistGet(
        @Path("actionName") actionName: String?,
        @Path("deviceID") deviceID: String?
    ) : Response<Playlist>

    @PUT("/api/devices/{deviceID}/{actionName}")
    suspend fun doActionMixed(
        @Path("actionName") actionName: String?,
        @Path("deviceID") deviceID: String?,
        @Body params: Array<*>,
    ): Response<Boolean>


    @PUT("/api/devices/{deviceID}/{actionName}")
    suspend fun doActionInt(
        @Path("actionName") actionName: String?,
        @Path("deviceID") deviceID: String?,
        @Body params: List<Int>
    ): Response<Int>

    @PUT("/api/devices/{deviceID}/{actionName}")
    suspend fun doActionString(
        @Path("actionName") actionName: String?,
        @Path("deviceID") deviceID: String?,
        @Body params: List<String>
    ): Response<String>

}