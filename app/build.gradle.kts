plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    // Hilt
    id("kotlin-kapt")
//    id("com.google.dagger.hilt.android") version "2.51.1" apply false
//    id("com.google.devtools.ksp") version "2.0.21-1.0.27"
    // Hilt
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.kotlin.compose)
    // room
    id("com.google.devtools.ksp")

    // serialization
//    kotlin("jvm") version "2.1.10"
    kotlin("plugin.serialization") version "2.1.10"
}

android {
    namespace = "com.sujith.kotlin.stocksapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sujith.kotlin.stocksapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // opencsv
    implementation(libs.opencsv)


    // viewmodel
//    val lifecycle_version = "2.8.7"
    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // ViewModel utilities for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Lifecycle utilities for Compose
    implementation(libs.androidx.lifecycle.runtime.compose)


    // compose nav
//    val nav_version = "2.8.8"
    implementation(libs.androidx.navigation.compose)

    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Hilt
    // When using Kotlin.
    implementation("com.google.dagger:hilt-android:2.55")
    kapt("com.google.dagger:hilt-compiler:2.55")
    kapt(libs.androidx.hilt.compiler)
    // Hilt navigation Compose
    implementation(libs.androidx.hilt.navigation.compose)


    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.moshi)
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.moshi)

    // Room
//    val room_version = "2.6.1"
    implementation(libs.androidx.room.runtime)
    // Kotlin source
    ksp(libs.androidx.room.compiler)
    // Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    // Swipe refresh
//    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-beta01")
//    implementation(libs.accompanist.swiperefresh)
    implementation("androidx.compose.material:material:1.7.8")

    // serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")


}

kapt {
    correctErrorTypes = true
}