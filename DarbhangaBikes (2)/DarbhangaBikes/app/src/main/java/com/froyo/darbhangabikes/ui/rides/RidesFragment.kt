package com.froyo.darbhangabikes.ui.rides

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.froyo.darbhangabikes.R
import com.froyo.darbhangabikes.data.model.Ride
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class RidesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmpty: TextView
    private val ridesAdapter = RidesAdapter()
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rides, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // UI Views dhoondna
        recyclerView = view.findViewById(R.id.rv_rides)
        progressBar = view.findViewById(R.id.progress_bar)
        tvEmpty = view.findViewById(R.id.tv_empty)

        // RecyclerView setup
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ridesAdapter

        fetchMyRides()
    }

    private fun fetchMyRides() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(context, "Please login first", Toast.LENGTH_SHORT).show()
            return
        }

        progressBar.visibility = View.VISIBLE

        // Database Query: Rides collection mein jahan riderId == meri ID
        firestore.collection("rides")
            .whereEqualTo("riderId", currentUser.uid)
            // .orderBy("createdAt", Query.Direction.DESCENDING) // Note: Iske liye Firestore index lag sakta hai
            .get()
            .addOnSuccessListener { documents ->
                progressBar.visibility = View.GONE
                
                if (documents.isEmpty) {
                    tvEmpty.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    tvEmpty.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    
                    // Documents ko Ride objects mein badalna
                    val rides = documents.toObjects(Ride::class.java)
                    ridesAdapter.setRides(rides)
                }
            }
            .addOnFailureListener { exception ->
                progressBar.visibility = View.GONE
                // Agar index error aaye toh log check karein, otherwise ignore karein
                Toast.makeText(context, "Error loading history", Toast.LENGTH_SHORT).show()
            }
    }
}