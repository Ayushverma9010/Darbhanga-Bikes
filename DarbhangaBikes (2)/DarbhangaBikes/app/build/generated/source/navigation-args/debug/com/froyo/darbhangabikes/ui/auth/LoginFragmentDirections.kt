package com.froyo.darbhangabikes.ui.auth

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.froyo.darbhangabikes.R

public class LoginFragmentDirections private constructor() {
  public companion object {
    public fun actionLoginToOtp(): NavDirections = ActionOnlyNavDirections(R.id.action_login_to_otp)
  }
}
