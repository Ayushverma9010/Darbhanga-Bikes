package com.froyo.darbhangabikes.data.repository

import android.util.Log
import com.froyo.darbhangabikes.data.model.Ride
import com.froyo.darbhangabikes.data.model.RideStatus
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlin.math.roundToInt

class RideRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val TAG = "RideRepository"
    
    // Commission Rate (Ex: 15% Platform rakhega, baaki Driver ka)
    private val COMMISSION_PERCENTAGE = 15.0 

    // 1. Ride Request bhejna
    suspend fun requestRide(ride: Ride): Result<String> {
        return try {
            val newRideRef = firestore.collection("rides").document()
            
            // Ride ID aur Commission set karke save karein
            val finalFare = ride.estimatedFare
            
            // Note: Commission calculation logic yahan hai agar future mein save karna ho
            // val platformCommission = (finalFare * COMMISSION_PERCENTAGE) / 100.0
            
            val rideWithDetails = ride.copy(
                rideId = newRideRef.id
            )
            
            newRideRef.set(rideWithDetails).await()
            Result.success(newRideRef.id)
        } catch (e: Exception) {
            Log.e(TAG, "Error requesting ride", e)
            Result.failure(e)
        }
    }

    // 2. Pricing Logic (Darbhanga Rates)
    fun calculateEstimatedFare(distanceInMeters: Double): Double {
        // Distance ko KM mein convert karein
        val rawKm = distanceInMeters / 1000.0
        
        // Round to 1 decimal place (Ex: 2.34 -> 2.3)
        val distanceInKm = (rawKm * 10.0).roundToInt() / 10.0
        
        var fare = 0.0

        // Rules Apply karein
        if (distanceInKm <= 1.0) {
            fare = 10.0
        } else if (distanceInKm <= 2.0) {
            fare = 13.0
        } else if (distanceInKm <= 3.0) {
            fare = 20.0
        } else if (distanceInKm <= 4.0) {
            fare = 24.0
        } else if (distanceInKm <= 5.0) {
            fare = 28.0
        } else {
            // > 5 km Logic: ₹28 + ₹6 per extra km
            val extraKm = distanceInKm - 5.0
            fare = 28.0 + (extraKm * 6.0)
        }

        // Round off to nearest integer (Ex: ₹34.2 -> ₹34.0)
        return fare.roundToInt().toDouble()
    }

    // Helper: Driver ki kamayi calculate karna
    fun calculateDriverEarning(totalFare: Double): Double {
        val commission = (totalFare * COMMISSION_PERCENTAGE) / 100.0
        return totalFare - commission
    }
    
    // 3. Cancel Ride
    suspend fun cancelRide(rideId: String): Result<Boolean> {
        return try {
            firestore.collection("rides").document(rideId)
                .update("status", RideStatus.CANCELLED)
                .await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ---------------------------------------------------------
    // NEW: LIVE TRACKING FEATURES 🚀
    // ---------------------------------------------------------

    // 4. Ride Updates Sunna (Listen for Real-time changes)
    // Jab bhi Driver location badlega, yeh function trigger hoga
    fun listenToRide(rideId: String, onRideUpdate: (Ride) -> Unit) {
        firestore.collection("rides").document(rideId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val ride = snapshot.toObject(Ride::class.java)
                    if (ride != null) {
                        onRideUpdate(ride)
                    }
                } else {
                    Log.d(TAG, "Current data: null")
                }
            }
    }

    // 5. FAKE DRIVER SIMULATOR (Sirf Testing ke liye)
    // Yeh function asli app mein Driver App ke andar hota hai.
    // Yahan hum Database mein fake updates bhejenge taaki car hilti hui dikhe.
    suspend fun simulateDriverMovement(rideId: String, startLat: Double, startLng: Double) {
        var currentLat = startLat
        var currentLng = startLng
        
        // Step 1: Driver Accept karega
        delay(3000) // 3 second wait
        firestore.collection("rides").document(rideId)
            .update(
                mapOf(
                    "status" to RideStatus.ACCEPTED,
                    "driverId" to "TEST_DRIVER_007",
                    "driverRating" to 4.8f
                )
            ).await()

        // Step 2: Driver chalna shuru karega (10 steps)
        for (i in 1..10) {
            delay(2000) // Har 2 second mein update
            
            // Fake Movement: Thoda North-East ki taraf
            currentLat += 0.0005 
            currentLng += 0.0005
            
            val newLocation = GeoPoint(currentLat, currentLng)
            
            val updates = hashMapOf<String, Any>(
                "status" to RideStatus.ONGOING,
                // Note: Ensure karein ki Ride.kt mein 'driverLocation' field ho
                // Agar nahi hai toh Ride.kt update karna padega
                 "driverLocation" to newLocation 
            )
            
            firestore.collection("rides").document(rideId).update(updates).await()
        }
        
        // Step 3: Ride Khatam
        delay(2000)
        firestore.collection("rides").document(rideId)
            .update("status", RideStatus.COMPLETED)
            .await()
    }
}