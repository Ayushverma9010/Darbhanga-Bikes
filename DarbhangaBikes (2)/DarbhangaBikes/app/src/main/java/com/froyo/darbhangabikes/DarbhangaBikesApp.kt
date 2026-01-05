package com.froyo.darbhangabikes


import com.froyo.darbhangabikes.BuildConfig // <--- ADD THIS LINE
// ...
import android.app.Application
import com.google.firebase.FirebaseApp
import timber.log.Timber

class DarbhangaBikesApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        
        Timber.d("DarbhangaBikesApp initialized")
    }
}
