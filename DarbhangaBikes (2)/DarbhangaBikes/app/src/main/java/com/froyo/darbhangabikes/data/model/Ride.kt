package com.froyo.darbhangabikes.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Ride(
    @DocumentId
    val rideId: String = "",
    val riderId: String = "",
    val driverId: String = "",
    val pickupLocation: GeoPoint? = null,
    val dropLocation: GeoPoint? = null,
    val pickupAddress: String = "",
    val dropAddress: String = "",
    val estimatedDistance: Double = 0.0,
    val estimatedDuration: Int = 0,
    val estimatedFare: Double = 0.0,
    val actualFare: Double = 0.0,
    val status: RideStatus = RideStatus.SEARCHING,
    val paymentMethod: PaymentMethod = PaymentMethod.CASH,
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING,
    val riderRating: Float = 0f,
    val driverRating: Float = 0f,
    val createdAt: Long = System.currentTimeMillis(),
    val acceptedAt: Long = 0L,
    val startedAt: Long = 0L,
    val completedAt: Long = 0L
)

enum class RideStatus {
    SEARCHING,      // Looking for driver
    ACCEPTED,       // Driver accepted
    ARRIVED,        // Driver arrived at pickup
    ONGOING,        // Ride in progress
    COMPLETED,      // Ride completed
    CANCELLED       // Ride cancelled
}

enum class PaymentMethod {
    CASH,
    UPI,
    CARD,
    WALLET
}

enum class PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED
}
