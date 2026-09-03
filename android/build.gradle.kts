group = "com.example.teelink_activity_launcher"
version = "1.0-SNAPSHOT"

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.example.teelink_activity_launcher"

    compileSdk = 35

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}
