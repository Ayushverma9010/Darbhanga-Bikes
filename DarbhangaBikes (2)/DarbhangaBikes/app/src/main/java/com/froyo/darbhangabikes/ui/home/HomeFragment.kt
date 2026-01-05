package com.froyo.darbhangabikes.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.froyo.darbhangabikes.R
import com.froyo.darbhangabikes.data.model.RideStatus
import com.froyo.darbhangabikes.data.repository.RideRepository
import com.froyo.darbhangabikes.ui.rides.BookingBottomSheetFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocation: Location? = null

    // ✅ Define UI Variables explicitly
    private lateinit var etDestination: EditText
    private lateinit var btnBookRide: Button

    // Logic Variables
    private val rideRepository = RideRepository()
    private var driverMarker: Marker? = null
    private var isTracking = false

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                setupLocation()
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
                val darbhanga = LatLng(26.1542, 85.9294)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(darbhanga, 15f))
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ✅ FIX: Connect variables to XML IDs properly
        etDestination = view.findViewById(R.id.et_destination)
        btnBookRide = view.findViewById(R.id.btn_book_ride)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        setFragmentResultListener("booking_request") { _, bundle ->
            val rideId = bundle.getString("ride_id")
            val status = bundle.getString("status")
            
            if (!rideId.isNullOrEmpty() && status == "SUCCESS") {
                startRealTracking(rideId)
            }
        }

        btnBookRide.setOnClickListener {
            if (isTracking) {
                Toast.makeText(requireContext(), "Ride already in progress", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ Use the variable 'etDestination', NOT the ID name directly
            val destination = etDestination.text.toString().trim()
            
            if (destination.isEmpty()) {
                etDestination.error = "Please enter destination"
                return@setOnClickListener
            }

            if (currentLocation == null) {
                Toast.makeText(requireContext(), "Waiting for location...", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Mock destination for now (Real app uses Geocoding API for coords)
            val fakeDestLat = currentLocation!!.latitude + 0.02
            val fakeDestLng = currentLocation!!.longitude + 0.02

            val bottomSheet = BookingBottomSheetFragment.newInstance(
                currentLocation!!.latitude,
                currentLocation!!.longitude,
                fakeDestLat,
                fakeDestLng,
                destination
            )
            bottomSheet.show(parentFragmentManager, "BookingBottomSheet")
        }
    }

    private fun startRealTracking(rideId: String) {
        isTracking = true
        btnBookRide.isEnabled = false
        btnBookRide.text = "Searching for Driver..."
        
        driverMarker?.remove()
        driverMarker = null

        rideRepository.listenToRide(rideId) { updatedRide ->
            when (updatedRide.status) {
                RideStatus.SEARCHING -> btnBookRide.text = "Searching for Driver..."
                RideStatus.ACCEPTED -> {
                    btnBookRide.text = "Driver Accepted!"
                    Toast.makeText(context, "Driver Found!", Toast.LENGTH_SHORT).show()
                }
                RideStatus.ARRIVED -> btnBookRide.text = "Driver has Arrived"
                RideStatus.ONGOING -> btnBookRide.text = "On Ride to Destination"
                RideStatus.COMPLETED -> {
                    btnBookRide.text = "Ride Completed"
                    btnBookRide.isEnabled = true
                    isTracking = false
                    driverMarker?.remove()
                }
                RideStatus.CANCELLED -> {
                    btnBookRide.text = "Ride Cancelled"
                    btnBookRide.isEnabled = true
                    isTracking = false
                    driverMarker?.remove()
                }
            }

            val dLoc = updatedRide.driverLocation
            if (dLoc != null && (updatedRide.status == RideStatus.ACCEPTED || updatedRide.status == RideStatus.ONGOING)) {
                val driverLatLng = LatLng(dLoc.latitude, dLoc.longitude)

                if (driverMarker == null) {
                    val markerOptions = MarkerOptions()
                        .position(driverLatLng)
                        .title("Driver")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    driverMarker = googleMap.addMarker(markerOptions)
                } else {
                    driverMarker?.position = driverLatLng
                }
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(driverLatLng))
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            setupLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun setupLocation() {
        googleMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                googleMap.addMarker(MarkerOptions().position(currentLatLng).title("You are here"))
            } else {
                val defaultLoc = LatLng(26.1542, 85.9294)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLoc, 15f))
            }
        }.addOnFailureListener {
            Log.e("HomeFragment", "Location error: ${it.message}")
        }
    }
}