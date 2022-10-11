package com.example.testallthings.ui.auth.OTP

import android.app.Activity
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
import com.example.testallthings.databinding.NewPasswrodFragmentBinding
import com.example.testallthings.ui.auth.OTP.OtpViewModel.Companion.phone
import com.example.testallthings.ui.auth.OTP.OtpViewModel.Companion.token
import com.example.testallthings.utils.ApiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPassword : Fragment() {
    lateinit var binding: NewPasswrodFragmentBinding
    private val viewModel: OtpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.new_passwrod_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveBtn.setOnClickListener {
            viewModel.changeUserPassword(
                phone,
                binding.newPassEt.text.toString(),
                binding.confirmNewPassEt.text.toString(),
                token
            )
        }

        viewModel.apistate.observe(viewLifecycleOwner) {
            when (it) {
                ApiState.Empty -> {}
                is ApiState.GenericError -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    binding.newPasswordProgressBar.visibility = View.INVISIBLE
                }
                ApiState.Loading -> binding.newPasswordProgressBar.visibility = View.VISIBLE
                ApiState.NetworkError -> {
                    Toast.makeText(activity, "Check Your internet Connection", Toast.LENGTH_SHORT)
                        .show()
                    binding.newPasswordProgressBar.visibility = View.INVISIBLE
                }
                is ApiState.Success<*> -> {
                    binding.newPasswordProgressBar.visibility = View.INVISIBLE
                    Toast.makeText(activity, "Password changed successfully", Toast.LENGTH_SHORT).show()
                    view.findNavController().navigate(R.id.action_newPassword_to_loginFragment)
                }
            }
        }
    }
}