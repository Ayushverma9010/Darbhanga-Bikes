package com.froyo.darbhangabikes.ui.rides

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.froyo.darbhangabikes.R
import com.froyo.darbhangabikes.data.model.Ride
import com.froyo.darbhangabikes.data.model.RideStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RidesAdapter : RecyclerView.Adapter<RidesAdapter.RideViewHolder>() {

    private val ridesList = mutableListOf<Ride>()

    fun setRides(rides: List<Ride>) {
        ridesList.clear()
        ridesList.addAll(rides)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ride, parent, false)
        return RideViewHolder(view)
    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        holder.bind(ridesList[position])
    }

    override fun getItemCount(): Int = ridesList.size

    class RideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // ✅ FIX: IDs must match your item_ride.xml
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        
        // Error yahan tha: tv_amount ki jagah tv_price use karein
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_price) 
        
        private val tvPickup: TextView = itemView.findViewById(R.id.tv_pickup_address)
        private val tvDrop: TextView = itemView.findViewById(R.id.tv_drop_address)
        private val tvStatus: TextView = itemView.findViewById(R.id.tv_status)

        fun bind(ride: Ride) {
            tvPrice.text = "₹${ride.estimatedFare}"
            tvPickup.text = ride.pickupAddress
            tvDrop.text = ride.dropAddress
            tvStatus.text = ride.status.name

            // Status Colors
            when (ride.status) {
                RideStatus.COMPLETED -> {
                    tvStatus.setTextColor(Color.parseColor("#2E7D32")) // Green
                    tvStatus.setBackgroundColor(Color.parseColor("#E8F5E9"))
                }
                RideStatus.CANCELLED -> {
                    tvStatus.setTextColor(Color.RED)
                    tvStatus.setBackgroundColor(Color.parseColor("#FFEBEE"))
                }
                else -> {
                    tvStatus.setTextColor(Color.DKGRAY)
                    tvStatus.setBackgroundColor(Color.parseColor("#F5F5F5"))
                }
            }

            // Date Formatting
            try {
                val sdf = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
                val date = Date(ride.createdAt)
                tvDate.text = sdf.format(date)
            } catch (e: Exception) {
                tvDate.text = "Recent"
            }
        }
    }
}