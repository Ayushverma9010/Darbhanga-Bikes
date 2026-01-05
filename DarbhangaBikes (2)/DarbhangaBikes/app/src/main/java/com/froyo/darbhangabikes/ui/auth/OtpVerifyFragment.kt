package com.froyo.darbhangabikes.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.froyo.darbhangabikes.R
import com.froyo.darbhangabikes.data.repository.AuthRepository
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch

class OtpVerifyFragment : Fragment() {

    private val authRepository = AuthRepository()
    private var verificationId: String? = null

    // UI Variables
    private lateinit var otpInput: EditText
    private lateinit var verifyButton: MaterialButton
    private lateinit var resendText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // LoginFragment se bheja hua ID receive karo
        arguments?.let {
            verificationId = it.getString("verificationId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_otp_verify, container, false)

        // 1. XML IDs ko link karein
        otpInput = view.findViewById(R.id.et_otp)
        verifyButton = view.findViewById(R.id.btn_verify)
        resendText = view.findViewById(R.id.tv_resend)

        // 2. Verify Button Logic
        verifyButton.setOnClickListener {
            val otpCode = otpInput.text.toString().trim()

            if (otpCode.length == 6 && verificationId != null) {
                // Button ko disable karein taaki user baar baar click na kare
                verifyButton.isEnabled = false
                verifyButton.text = "Verifying..."
                
                verifyOtpAndLogin(otpCode)
            } else {
                Toast.makeText(context, "Please enter a valid 6-digit OTP", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. Resend Logic (Optional - abhi ke liye toast laga diya hai)
        resendText.setOnClickListener {
            Toast.makeText(context, "Resend feature coming soon", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun verifyOtpAndLogin(otp: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)

        viewLifecycleOwner.lifecycleScope.launch {
            // Repository call: Login + Referral Code Creation
            val result = authRepository.signInWithPhone(credential)

            if (result.isSuccess) {
                Toast.makeText(context, "Welcome to Darbhanga Bikes!", Toast.LENGTH_SHORT).show()
                // Home screen par bhejein
                findNavController().navigate(R.id.action_otp_to_home)
            } else {
                // Fail hone par button wapas enable karein
                verifyButton.isEnabled = true
                verifyButton.text = "Verify & Continue"
                Toast.makeText(context, "Error: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}