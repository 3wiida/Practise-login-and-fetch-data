package com.example.testallthings.ui.auth.OTP

import com.example.testallthings.network.Api
import com.example.testallthings.network.model.ForgotPasswordResponse.ForgotPasswordResponse
import com.example.testallthings.utils.ResultWrapper
import com.example.testallthings.utils.performSafeApiCall
import javax.inject.Inject

class OtpRepository @Inject constructor(val api:Api) {

    suspend fun checkUserExistent(phoneNumber:String):ResultWrapper<ForgotPasswordResponse>{
        return performSafeApiCall { api.createPassword(phoneNumber) }
    }

    suspend fun changeUserPassword(phoneNumber: String,newPass:String,confirmPass:String,token:String):ResultWrapper<Unit>{
        return performSafeApiCall { api.resetPassword(phoneNumber,newPass,confirmPass,token) }
    }
}