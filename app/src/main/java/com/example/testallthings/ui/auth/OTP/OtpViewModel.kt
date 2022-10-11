package com.example.testallthings.ui.auth.OTP

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testallthings.utils.ApiState
import com.example.testallthings.utils.FirebaseUtils
import com.example.testallthings.utils.ResultWrapper
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(val repo:OtpRepository): ViewModel(){

    private var _sendCodeState=MutableLiveData<String>()
    var sendCodeState:LiveData<String> = _sendCodeState

    private var _apiState=MutableLiveData<ApiState>()
    var apistate:LiveData<ApiState> =_apiState

    fun sendOtpCode(context: Context, phoneNumber:String){
        var options=PhoneAuthOptions.newBuilder().apply {
            setPhoneNumber(phoneNumber)
            setTimeout(60L,TimeUnit.SECONDS)
            setCallbacks(callBacks)
            setActivity(context as Activity)
        }.build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callBacks= object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            signInWithCredential(p0)
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            _sendCodeState.postValue(p0.message)
        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            code=p0
            resendToken=p1
        }
    }

     fun signInWithCredential(credential: PhoneAuthCredential){
        FirebaseUtils.auth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                _sendCodeState.postValue("success")
            }else{
                if (it.exception is FirebaseAuthInvalidCredentialsException) {
                    _sendCodeState.postValue("invalid code")
                }else{
                    _sendCodeState.postValue(it.exception?.message)
                }
            }
        }
    }


    fun checkUserExtant(phone:String){
        _apiState.postValue(ApiState.Loading)
        viewModelScope.launch(Dispatchers.IO){
            when(val response=repo.checkUserExistent(phone)){
                is ResultWrapper.GenericError -> _apiState.postValue(ApiState.GenericError(response.code,response.message))
                ResultWrapper.NetworkError -> _apiState.postValue(ApiState.NetworkError)
                is ResultWrapper.Success ->  {
                    _apiState.postValue(ApiState.Success(response.result))
                    token=response.result.code
                }
            }
        }
    }

    fun changeUserPassword(phone:String,newPass:String,confirmPass:String,token:String){
        _apiState.postValue(ApiState.Loading)
        viewModelScope.launch(Dispatchers.IO){
            when(val response=repo.changeUserPassword(phone,newPass,confirmPass,token)){
                is ResultWrapper.GenericError -> _apiState.postValue(ApiState.GenericError(response.code,response.message))
                ResultWrapper.NetworkError -> _apiState.postValue(ApiState.NetworkError)
                is ResultWrapper.Success -> _apiState.postValue(ApiState.Success(response.result))
            }
        }
    }

    companion object{
        lateinit var code:String
        lateinit var resendToken:PhoneAuthProvider.ForceResendingToken
        lateinit var phone:String
        lateinit var token:String
    }
}