package com.example.testallthings.ui.auth.OTP

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
import com.example.testallthings.databinding.EnterOtpFragmentBinding
import com.example.testallthings.ui.auth.OTP.OtpViewModel.Companion.code
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EnterOtp : Fragment() {

    lateinit var binding: EnterOtpFragmentBinding
    private val viewModel: OtpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.enter_otp_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.enterOtpVerifyBtn.setOnClickListener {
            val credential =
                PhoneAuthProvider.getCredential(code, binding.enterOtpPv.text.toString())
            viewModel.signInWithCredential(credential)
        }

        viewModel.sendCodeState.observe(viewLifecycleOwner) {
            if (it == "success") {
                view.findNavController().navigate(R.id.action_enterOtp_to_newPassword)
            } else {
                Toast.makeText(activity, "Invalid Code", Toast.LENGTH_SHORT).show()
            }
        }

    }
}