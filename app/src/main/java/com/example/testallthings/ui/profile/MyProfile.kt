package com.example.testallthings.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.testallthings.R
import com.example.testallthings.common.Constants.IMAGE_PREFIX
import com.example.testallthings.databinding.ActivityMyProfileBinding
import com.example.testallthings.network.model.profile.ProfileResponse
import com.example.testallthings.ui.auth.LoginActivity
import com.example.testallthings.utils.ApiState
import com.example.testallthings.utils.prefUtils.PrefKeys
import com.example.testallthings.utils.prefUtils.PrefUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyProfile : AppCompatActivity() {

    lateinit var binding: ActivityMyProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile)
        viewModel.getProfileData()
        viewModel.apiState.observe(this) {
            when (it) {
                ApiState.Empty -> {}
                is ApiState.GenericError -> {
                    binding.profileProgressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                ApiState.Loading -> {
                    binding.profileProgressBar.visibility = View.VISIBLE
                }
                ApiState.NetworkError -> {
                    binding.profileProgressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "please check your internet connection", Toast.LENGTH_SHORT).show()
                    Log.d("3wiida", "onCreate: Entered network error")
                }
                is ApiState.Success<*> -> {
                    Log.d("3wiida", "onCreate: Entered success")
                    var profileData = it.result as ProfileResponse
                    binding.userProfileName.visibility=View.VISIBLE
                    binding.userProfileData.visibility=View.VISIBLE
                    binding.userProfileImage.visibility=View.VISIBLE
                    Glide.with(this).load("${IMAGE_PREFIX}${profileData.user.image}").fitCenter().circleCrop().into(binding.userProfileImage)
                    binding.userProfileName.text = profileData.user.name
                    binding.userProfileData.text = profileData.user.phone
                    binding.profileProgressBar.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_option_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logoutOption ->{
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }
        return true
    }
}