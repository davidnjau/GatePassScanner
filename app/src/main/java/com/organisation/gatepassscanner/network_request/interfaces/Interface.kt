package com.organisation.gatepassscanner.network_request.interfaces

import com.organisation.gatepassscanner.dto.*
import retrofit2.Call
import retrofit2.http.*


interface Interface {

    @POST("/api/v1/auth/login")
    fun loginUser(@Body user: UserLogin): Call<LoginResponse>

}