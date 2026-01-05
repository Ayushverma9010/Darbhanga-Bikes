package com.froyo.darbhangabikes.ui.auth

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.froyo.darbhangabikes.R

public class OtpVerifyFragmentDirections private constructor() {
  public companion object {
    public fun actionOtpToHome(): NavDirections = ActionOnlyNavDirections(R.id.action_otp_to_home)
  }
}
