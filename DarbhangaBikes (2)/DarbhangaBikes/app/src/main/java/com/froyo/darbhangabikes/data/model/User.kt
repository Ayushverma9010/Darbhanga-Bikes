package com.froyo.darbhangabikes.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    @DocumentId
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val profileImageUrl: String = "",
    val userType: UserType = UserType.RIDER,
    val rating: Float = 0f,
    val totalRides: Int = 0,
    val isActive: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class UserType {
    RIDER,      // Customer who books rides
    DRIVER      // Bike rider who provides service
}
