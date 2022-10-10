package com.example.testallthings.ui.auth.login

import com.example.testallthings.network.Api
import com.example.testallthings.network.model.loginResponse.User
import com.example.testallthings.utils.ResultWrapper
import com.example.testallthings.utils.performSafeApiCall
import javax.inject.Inject

class LoginRepository @Inject constructor(val api:Api) {

    suspend fun login(phone:String,pass:String):ResultWrapper<User>{
        return performSafeApiCall { api.login(phone,pass) }
    }
}