# Darbhanga Bikes - Bike Ride Booking App

A modern Android application for booking bike rides, built with Kotlin, Firebase, and Google Maps.

## Features

- 🚴 Book bike rides with real-time tracking
- 🔐 Multiple authentication methods (Phone OTP & Google Sign-In)
- 🗺️ Google Maps integration for location selection
- 🌍 Multi-language support (English, Hindi, Tamil, Telugu, Kannada)
- 📱 Clean Material Design UI
- 💾 Firebase backend integration
- 🏗️ MVVM Architecture

## Tech Stack

- **Language:** Kotlin
- **Architecture:** MVVM (Model-View-ViewModel)
- **Backend:** Firebase (Auth, Firestore, Realtime Database)
- **Maps:** Google Maps SDK
- **Navigation:** Android Navigation Component
- **Image Loading:** Glide
- **Animations:** Lottie
- **Networking:** Retrofit + OkHttp
- **Permissions:** PermissionX
- **Database:** Room

## Prerequisites

Before you begin, ensure you have the following:

1. **Android Studio** (Latest version - Arctic Fox or newer)
2. **JDK 11** or higher
3. **Firebase Account**
4. **Google Cloud Console Account** (for Maps API)

## Setup Instructions

### 1. Clone or Download the Project

Extract the project to your desired location.

### 2. Open Project in Android Studio

1. Launch Android Studio
2. Click on "Open an Existing Project"
3. Navigate to the project folder and select it
4. Wait for Gradle sync to complete

### 3. Firebase Setup

#### Step 1: Create Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click "Add Project"
3. Enter project name: "Darbhanga Bikes" (or your preferred name)
4. Follow the setup wizard

#### Step 2: Add Android App to Firebase

1. In Firebase Console, click on "Add app" → Android
2. Enter package name: `com.froyo.darbhangabikes`
3. Enter app nickname: "Darbhanga Bikes"
4. Generate SHA-1 and SHA-256 fingerprints:

**For Debug Keystore:**
```bash
keytool -list -v -alias androiddebugkey -keystore ~/.android/debug.keystore
```
Default password: `android`

**For Release Keystore (if you have one):**
```bash
keytool -list -v -alias YOUR_ALIAS -keystore YOUR_KEYSTORE_PATH
```

5. Enter the SHA-1 and SHA-256 fingerprints in Firebase
6. Click "Register App"

#### Step 3: Download google-services.json

1. Download the `google-services.json` file from Firebase Console
2. Replace the template file at `/app/google-services.json` with your downloaded file

#### Step 4: Enable Firebase Services

In Firebase Console, enable the following services:

1. **Authentication:**
   - Email/Password
   - Phone
   - Google Sign-In

2. **Cloud Firestore:**
   - Create a database (Start in test mode for development)

3. **Realtime Database:**
   - Create a database (Start in test mode for development)

4. **Storage:**
   - Create a storage bucket (Start in test mode for development)

### 4. Google Maps Setup

#### Step 1: Get Google Maps API Key

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select existing project
3. Enable the following APIs:
   - Maps SDK for Android
   - Places API
   - Directions API
   - Geocoding API

4. Create credentials → API Key
5. Copy your API key

#### Step 2: Add API Key to Project

The API key is already configured in the project:
- Your API Key: `AIzaSyAVc7-bftMhFsDwsVk8B8yczSM2PkI8Bs0`
- Location: `app/build.gradle` → `manifestPlaceholders`

**Important:** For production, restrict your API key:
1. Go to Google Cloud Console
2. Find your API key
3. Add application restrictions (Android apps)
4. Add API restrictions (only the APIs you're using)
5. Add your package name and SHA-1 fingerprint

### 5. Update Package Name (Optional)

If you want to change the package name:

1. Update in `app/build.gradle`:
   ```gradle
   defaultConfig {
       applicationId "com.froyo.YOUR_PACKAGE_NAME"
   }
   ```

2. Update in `AndroidManifest.xml`:
   ```xml
   <manifest xmlns:android="http://schemas.android.com/apk/res/android"
       package="com.froyo.YOUR_PACKAGE_NAME">
   ```

3. Refactor package name in Android Studio:
   - Right-click on package folder
   - Refactor → Rename
   - Update all references

### 6. Build the Project

1. In Android Studio, click **Build → Clean Project**
2. Click **Build → Rebuild Project**
3. Wait for build to complete

### 7. Run the App

1. Connect an Android device via USB or start an emulator
2. Click the "Run" button (green play icon) or press `Shift + F10`
3. Select your device and click OK

## Project Structure

```
DarbhangaBikes/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/froyo/darbhangabikes/
│   │       │   ├── data/
│   │       │   │   └── model/          # Data models
│   │       │   ├── ui/
│   │       │   │   ├── home/           # Home fragment
│   │       │   │   ├── rides/          # Rides history
│   │       │   │   ├── profile/        # User profile
│   │       │   │   └── MainActivity.kt
│   │       │   └── DarbhangaBikesApp.kt
│   │       ├── res/
│   │       │   ├── layout/             # XML layouts
│   │       │   ├── values/             # Strings, colors, themes
│   │       │   ├── navigation/         # Navigation graphs
│   │       │   └── menu/               # Menu resources
│   │       └── AndroidManifest.xml
│   ├── build.gradle
│   └── google-services.json            # Your Firebase config
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Common Issues & Solutions

### Issue 1: 401 Unauthorized from JitPack

**Solution:** This project uses alternative libraries instead of problematic JitPack dependencies:
- Runtime Permission: Using `PermissionX` instead of `runtime-permission-kotlin`
- Network: Using `Retrofit` instead of `Volley`

### Issue 2: Google Sign-In Not Working

**Solution:**
1. Ensure SHA-1 and SHA-256 fingerprints are added to Firebase
2. Download the latest `google-services.json`
3. Enable Google Sign-In in Firebase Authentication

### Issue 3: Maps Not Loading

**Solution:**
1. Check if Maps API key is valid
2. Ensure Maps SDK for Android is enabled in Google Cloud Console
3. Check if API key restrictions allow your app

### Issue 4: Build Fails

**Solution:**
1. Clean project: `Build → Clean Project`
2. Invalidate caches: `File → Invalidate Caches / Restart`
3. Delete `.gradle` folder and rebuild
4. Ensure you're using JDK 11 or higher

## Configuration Files

### Important Gradle Configuration

The project uses:
- **Gradle Version:** 8.2
- **Android Gradle Plugin:** 8.1.4
- **Kotlin Version:** 1.9.20
- **Compile SDK:** 34
- **Min SDK:** 21
- **Target SDK:** 34

### Key Dependencies

- Firebase BoM: 32.7.0
- Google Maps: 18.2.0
- Navigation Component: 2.7.5
- Lifecycle: 2.6.2
- Coroutines: 1.7.3
- Retrofit: 2.9.0
- Glide: 4.16.0
- Room: 2.6.1

## Testing

### Debug Build
```bash
./gradlew assembleDebug
```

### Run Tests
```bash
./gradlew test
```

### Generate APK
```bash
./gradlew assembleRelease
```

## Features Implementation Status

- ✅ Project structure setup
- ✅ Firebase integration
- ✅ Google Maps integration
- ✅ Navigation setup
- ✅ Multi-language support (strings)
- ⏳ Authentication implementation (placeholder)
- ⏳ Ride booking flow (placeholder)
- ⏳ Real-time tracking (to be implemented)
- ⏳ Payment integration (to be implemented)

## Next Steps for Development

1. **Implement Authentication:**
   - Phone OTP verification
   - Google Sign-In
   - User profile management

2. **Implement Ride Booking:**
   - Location selection
   - Rider search
   - Fare calculation
   - Ride tracking

3. **Add Real-time Features:**
   - Live location tracking
   - Driver-rider communication
   - Ride status updates

4. **Implement Payment:**
   - Multiple payment methods
   - Payment gateway integration
   - Transaction history

5. **Add Additional Features:**
   - Ride history
   - Ratings and reviews
   - Contact list invite
   - Push notifications

## Contributing

This is a template project. Feel free to customize and extend it according to your needs.

## License

This project is provided as-is for educational and commercial purposes.

## Support

For issues related to:
- **Firebase:** Check [Firebase Documentation](https://firebase.google.com/docs)
- **Google Maps:** Check [Maps SDK Documentation](https://developers.google.com/maps/documentation/android-sdk)
- **Android Development:** Check [Android Developer Guide](https://developer.android.com/)

## Important Notes

1. **API Keys:** Never commit real API keys to public repositories
2. **Firebase Rules:** Update security rules before production
3. **Testing:** Always test on multiple devices and Android versions
4. **Permissions:** Handle runtime permissions properly
5. **ProGuard:** Configure ProGuard rules for release builds

---

**Built with ❤️ for Darbhanga Bikes**
