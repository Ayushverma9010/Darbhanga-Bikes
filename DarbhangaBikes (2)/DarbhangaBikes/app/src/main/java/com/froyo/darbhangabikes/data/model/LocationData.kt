package com.froyo.darbhangabikes.data.model

import android.location.Location

data class LocationData(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "",
    val timestamp: Long = System.currentTimeMillis()
) {
    companion object {
        fun from(location: Location): LocationData {
            return LocationData(
                latitude = location.latitude,
                longitude = location.longitude,
                timestamp = location.time
            )
        }
    }
}
