package com.example.testallthings.ui.auth.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testallthings.network.model.loginResponse.User
import com.example.testallthings.utils.ApiState
import com.example.testallthings.utils.ResultWrapper
import com.example.testallthings.utils.prefUtils.PrefKeys
import com.example.testallthings.utils.prefUtils.PrefKeys.USER_TOKEN
import com.example.testallthings.utils.prefUtils.PrefUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(@ApplicationContext val context:Context,val repo:LoginRepository):ViewModel() {
    private val TAG="3wiida"
    private var _loginStateMutableLiveData=MutableLiveData<ApiState>()
    var loginStateLiveData:LiveData<ApiState> = _loginStateMutableLiveData

    fun loginUser(phone:String,pass:String){
        viewModelScope.launch(Dispatchers.IO){
            _loginStateMutableLiveData.postValue(ApiState.Loading)
            when(val response=repo.login(phone,pass)){
                is ResultWrapper.GenericError -> _loginStateMutableLiveData.postValue(ApiState.GenericError(response.code,response.message))
                ResultWrapper.NetworkError -> _loginStateMutableLiveData.postValue(ApiState.NetworkError)
                is ResultWrapper.Success -> {
                    _loginStateMutableLiveData.postValue(ApiState.Success(response.result))
                    PrefUtils.saveToPref(context, USER_TOKEN,response.result.access_token)
                }
            }
        }
    }
}