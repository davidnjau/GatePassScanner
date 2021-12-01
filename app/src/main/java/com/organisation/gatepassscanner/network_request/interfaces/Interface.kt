package com.organisation.gatepassscanner.network_request.interfaces

import com.organisation.gatepassscanner.dto.*
import retrofit2.Call
import retrofit2.http.*


interface Interface {

    @POST("/api/v1/auth/login")
    fun loginUser(@Body user: UserLogin): Call<LoginResponse>

    @POST("/api/v1/time-status/add-time")
    fun addTimeStatus(@Body dbStaffTime: DbStaffTime,@HeaderMap headers: Map<String, String>): Call<Results>

}