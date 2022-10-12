package com.example.testallthings.ui.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testallthings.utils.ApiState
import com.example.testallthings.utils.ResultWrapper
import com.example.testallthings.utils.prefUtils.PrefKeys
import com.example.testallthings.utils.prefUtils.PrefUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(@ApplicationContext val context: Context, val repo:ProfileRepository):ViewModel() {

    var _apiState=MutableLiveData<ApiState>()
    var apiState:LiveData<ApiState> = _apiState

    fun getProfileData(){
        viewModelScope.launch(Dispatchers.IO){
            _apiState.postValue(ApiState.Loading)
            Log.d("3wiida", PrefUtils.getFromPref(context, PrefKeys.USER_TOKEN, "").toString())
            when(val response=repo.getUserProfile()){
                is ResultWrapper.GenericError -> _apiState.postValue(ApiState.GenericError(response.code,response.message))
                ResultWrapper.NetworkError -> _apiState.postValue(ApiState.NetworkError)
                is ResultWrapper.Success -> _apiState.postValue(ApiState.Success(response.result))
            }
        }
    }
}