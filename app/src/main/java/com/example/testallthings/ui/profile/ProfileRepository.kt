package com.example.testallthings.ui.profile

import com.example.testallthings.network.Api
import com.example.testallthings.network.model.profile.ProfileResponse
import com.example.testallthings.utils.ResultWrapper
import com.example.testallthings.utils.performSafeApiCall
import javax.inject.Inject

class ProfileRepository @Inject constructor(val api:Api) {

    suspend fun getUserProfile():ResultWrapper<ProfileResponse>{
       return  performSafeApiCall { api.getProfile() }
    }
}