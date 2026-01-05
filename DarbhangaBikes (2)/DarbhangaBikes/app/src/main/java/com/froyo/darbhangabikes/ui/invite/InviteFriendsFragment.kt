package com.froyo.darbhangabikes.ui.invite

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.froyo.darbhangabikes.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class InviteFriendsFragment : Fragment() {

    private lateinit var tvReferralCode: TextView
    private lateinit var btnShare: Button
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_invite_friends, container, false)

        tvReferralCode = view.findViewById(R.id.tv_referral_code)
        btnShare = view.findViewById(R.id.btn_share_whatsapp)
        
        // 1. Database se User ka Code fetch karo
        fetchUserReferralCode()

        // 2. Code Copy karne ka logic
        tvReferralCode.setOnClickListener {
            val code = tvReferralCode.text.toString()
            copyToClipboard(code)
        }

        // 3. WhatsApp Share karne ka logic
        btnShare.setOnClickListener {
            val code = tvReferralCode.text.toString()
            shareOnWhatsApp(code)
        }

        return view
    }

    private fun fetchUserReferralCode() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // Database se "myReferralCode" field padho
                        val code = document.getString("myReferralCode")
                        if (!code.isNullOrEmpty()) {
                            tvReferralCode.text = code
                        } else {
                            // Agar code nahi hai, to naya banao aur save karo
                            val newCode = generateNewCode()
                            saveCodeToDb(userId, newCode)
                            tvReferralCode.text = newCode
                        }
                    }
                }
                .addOnFailureListener {
                    tvReferralCode.text = "ERROR"
                }
        } else {
            tvReferralCode.text = "LOGIN FIRST"
        }
    }

    private fun generateNewCode(): String {
        // Simple random code logic: 2 Letters + 4 Numbers (e.g., AB1234)
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val randomString = (1..2).map { chars.random() }.joinToString("")
        val randomNum = (1000..9999).random()
        return "$randomString$randomNum" 
    }

    private fun saveCodeToDb(userId: String, code: String) {
        val data = hashMapOf("myReferralCode" to code)
        db.collection("users").document(userId)
            .update(data as Map<String, Any>)
    }

    private fun copyToClipboard(code: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Referral Code", code)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Code Copied: $code", Toast.LENGTH_SHORT).show()
    }

    private fun shareOnWhatsApp(code: String) {
        val message = "Hey! Join Darbhanga Bikes using my code *$code* and get ₹50 off on your first ride! Download App Link: https://play.google.com/store/apps/details?id=com.froyo.darbhangabikes"
        
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.setPackage("com.whatsapp") // Sirf WhatsApp open karega
        intent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            startActivity(intent)
        } catch (e: Exception) {
            // Agar WhatsApp install nahi hai
            Toast.makeText(context, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
        }
    }
}