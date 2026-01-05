package com.froyo.darbhangabikes.data.repository

import com.froyo.darbhangabikes.data.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")

    fun getCurrentUser() = auth.currentUser

    // Phone Sign In
    suspend fun signInWithPhone(credential: PhoneAuthCredential): Result<User> {
        return try {
            val result = auth.signInWithCredential(credential).await()
            val user = result.user
            if (user != null) {
                // Pass empty email/name as Phone Auth doesn't provide them initially
                val userModel = createOrGetUser(
                    uid = user.uid,
                    phone = user.phoneNumber ?: ""
                )
                Result.success(userModel)
            } else {
                Result.failure(Exception("Sign in failed: User is null"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Phone sign in failed")
            Result.failure(e)
        }
    }

    // Google Sign In
    suspend fun signInWithGoogle(account: GoogleSignInAccount): Result<User> {
        return try {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val result = auth.signInWithCredential(credential).await()
            val user = result.user
            if (user != null) {
                val userModel = createOrGetUser(
                    uid = user.uid,
                    phone = user.phoneNumber ?: "",
                    name = user.displayName ?: "",
                    email = user.email ?: ""
                )
                Result.success(userModel)
            } else {
                Result.failure(Exception("Sign in failed: User is null"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Google sign in failed")
            Result.failure(e)
        }
    }

    // ----------------------------------------------------------------
    // CORE LOGIC: Create User with Unique Referral Code
    // ----------------------------------------------------------------
    private suspend fun createOrGetUser(
        uid: String,
        phone: String,
        name: String = "",
        email: String = ""
    ): User {
        // 1. Check if user already exists
        val userDoc = usersCollection.document(uid).get().await()

        return if (userDoc.exists()) {
            // User exists, return their data
            val existingUser = userDoc.toObject(User::class.java)
            existingUser ?: User(uid = uid) // Fallback if parsing fails
        } else {
            // 2. NEW USER: We must generate a UNIQUE referral code
            val uniqueReferralCode = generateUniqueReferralCode()

            val newUser = User(
                uid = uid,
                phoneNumber = phone,
                name = name,
                email = email,
                myReferralCode = uniqueReferralCode, // Generated Code
                referredBy = "", // Initially empty, can be updated later via Profile
                walletBalance = 0
            )

            // 3. Save to Firestore
            usersCollection.document(uid).set(newUser).await()
            Timber.d("New user created with code: $uniqueReferralCode")
            newUser
        }
    }

    // Recursive function to ensure code is unique
    private suspend fun generateUniqueReferralCode(): String {
        var isUnique = false
        var code = ""

        while (!isUnique) {
            code = generateRandomCode()
            // Check Firestore to see if this code is already taken
            val querySnapshot = usersCollection
                .whereEqualTo("myReferralCode", code)
                .get()
                .await()

            if (querySnapshot.isEmpty) {
                isUnique = true
            } else {
                Timber.d("Collision detected for code $code, generating new one...")
            }
        }
        return code
    }

    // Simple helper to create random string
    private fun generateRandomCode(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..6)
            .map { chars.random() }
            .joinToString("")
    }
}