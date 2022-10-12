package com.example.testallthings.network

import com.example.testallthings.network.model.ForgotPasswordResponse.ForgotPasswordResponse
import com.example.testallthings.network.model.loginResponse.User
import com.example.testallthings.network.model.profile.ProfileResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("phone") phone:String,
        @Field("password") password:String,
        @Field("imei") imei:String="123",
        @Field("token") token:String="123",
        @Field("device_type") device_type:String="android"
    ): User

    @FormUrlEncoded
    @POST("password/create")
    suspend fun createPassword(@Field("email") phone:String): ForgotPasswordResponse

    @FormUrlEncoded
    @POST("password/reset")
    suspend fun resetPassword(
        @Field("email") phone:String,
        @Field("password") password:String,
        @Field("confirm_password") confirm_password:String,
        @Field("token") token:String
    )

    @GET("profile")
    suspend fun getProfile():ProfileResponse
}