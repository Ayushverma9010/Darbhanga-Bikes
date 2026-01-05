package com.froyo.darbhangabikes.ui.home

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.froyo.darbhangabikes.R

public class HomeFragmentDirections private constructor() {
  public companion object {
    public fun actionHomeToRideStatus(): NavDirections =
        ActionOnlyNavDirections(R.id.action_home_to_rideStatus)

    public fun actionHomeToBookingSheet(): NavDirections =
        ActionOnlyNavDirections(R.id.action_home_to_bookingSheet)
  }
}
