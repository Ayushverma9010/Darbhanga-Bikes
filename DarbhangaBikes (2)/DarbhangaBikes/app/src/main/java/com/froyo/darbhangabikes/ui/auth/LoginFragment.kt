package com.froyo.darbhangabikes.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.froyo.darbhangabikes.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginFragment : Fragment() {

    private lateinit var phoneInput: EditText
    private lateinit var loginButton: MaterialButton
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()

        // ⚠️ DHYAN DEIN: Ye IDs aapke XML file se match honi chahiye
        phoneInput = view.findViewById(R.id.et_phone_number) // XML mein EditText ki ID check karein
        loginButton = view.findViewById(R.id.btn_continue)   // XML mein Button ki ID check karein

        loginButton.setOnClickListener {
            val number = phoneInput.text.toString().trim()

            if (number.isNotEmpty()) {
                if (number.length == 10) {
                    // +91 jod kar OTP bhejein
                    sendOtp("+91$number")
                } else {
                    Toast.makeText(context, "Please enter valid 10-digit number", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Phone number required", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun sendOtp(phoneNumber: String) {
        // Button disable karein taaki user baar-baar click na kare
        loginButton.isEnabled = false
        loginButton.text = "Sending OTP..."

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())    // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Auto-verification (agar kabhi automatic ho jaye)
                    // Abhi ke liye hum manual OTP enter karvayenge
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // Agar OTP bhejne mein fail ho jaye
                    loginButton.isEnabled = true
                    loginButton.text = "Get OTP"
                    Toast.makeText(context, "Failed: ${e.message}", Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    // SUCCESS! OTP chala gaya
                    loginButton.isEnabled = true
                    loginButton.text = "Get OTP"
                    
                    // OTP Screen par bhejein aur Verification ID saath le jayen
                    val bundle = Bundle()
                    bundle.putString("verificationId", verificationId)
                    
                    try {
                        findNavController().navigate(R.id.action_login_to_otp, bundle)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Navigation Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}