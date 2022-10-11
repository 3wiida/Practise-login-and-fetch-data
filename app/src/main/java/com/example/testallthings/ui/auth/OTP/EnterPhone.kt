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
import com.example.testallthings.databinding.EnterPhoneFragmentBinding
import com.example.testallthings.ui.auth.OTP.OtpViewModel.Companion.phone
import com.example.testallthings.utils.ApiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterPhone : Fragment() {
    lateinit var binding: EnterPhoneFragmentBinding
    private val viewModel: OtpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.enter_phone_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.enterPhoneSendCodeBtn.setOnClickListener {
            viewModel.checkUserExtant(binding.enterPhoneFragmentPhoneEt.text.toString())
        }

        viewModel.apistate.observe(viewLifecycleOwner) {
            when (it) {
                ApiState.Empty -> {}
                is ApiState.GenericError -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                    binding.enterPhoneProgressBar.visibility = View.INVISIBLE
                }
                ApiState.Loading -> binding.enterPhoneProgressBar.visibility = View.VISIBLE
                ApiState.NetworkError -> {
                    Toast.makeText(activity, "Check Your internet Connection", Toast.LENGTH_SHORT)
                        .show()
                    binding.enterPhoneProgressBar.visibility = View.INVISIBLE
                }
                is ApiState.Success<*> -> {
                    viewModel.sendOtpCode(
                        activity as Activity,
                        "+2${binding.enterPhoneFragmentPhoneEt.text}"
                    )
                    view.findNavController().navigate(R.id.action_enterPhone_to_enterOtp)
                    phone = binding.enterPhoneFragmentPhoneEt.text.toString()
                    binding.enterPhoneProgressBar.visibility = View.INVISIBLE
                }
            }
        }
    }


}