package com.example.testallthings.ui.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.testallthings.utils.prefUtils.PrefKeys.USER_TOKEN
import com.example.testallthings.utils.prefUtils.PrefUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel

class SplashViewModel @Inject constructor(@ApplicationContext val context: Context) :ViewModel() {
    fun checkLoginState():Boolean{
        return PrefUtils.getFromPref(context,USER_TOKEN,"") != ""
    }
}