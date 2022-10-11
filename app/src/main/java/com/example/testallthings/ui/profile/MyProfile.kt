package com.example.testallthings.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testallthings.R
import com.example.testallthings.databinding.ActivityMyProfileBinding

class MyProfile : AppCompatActivity() {
    lateinit var binding:ActivityMyProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
    }
}