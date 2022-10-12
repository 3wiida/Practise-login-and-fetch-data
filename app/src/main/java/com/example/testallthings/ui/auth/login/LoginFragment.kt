package com.example.testallthings.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.testallthings.R
import com.example.testallthings.databinding.LoginFragmentLayoutBinding
import com.example.testallthings.ui.profile.MyProfile
import com.example.testallthings.utils.ApiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding: LoginFragmentLayoutBinding
    val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.login_fragment_layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginLoginBtn.setOnClickListener {
            viewModel.loginUser(
                binding.loginPhoneEt.text.toString(),
                binding.loginPasswordEt.text.toString()
            )
        }

        binding.loginForgotPasswordTv.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_enterPhone2)
        }

        viewModel.loginStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ApiState.Empty -> {}
                ApiState.Loading -> binding.loginProgressBar.visibility = View.VISIBLE
                ApiState.NetworkError -> {
                    binding.loginProgressBar.visibility = View.GONE
                    Toast.makeText(activity, "Check you internet connection", Toast.LENGTH_SHORT).show()
                }
                is ApiState.GenericError -> {

                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
                is ApiState.Success<*> -> {
                    binding.loginProgressBar.visibility = View.GONE
                    activity?.startActivity(Intent(activity, MyProfile::class.java))
                    activity?.finish()
                }
            }
        }
    }
}