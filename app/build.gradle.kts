plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    kotlin("kapt")     ////for dagger hilt
    id("com.google.dagger.hilt.android") ////for dagger hilt

   // kotlin("android.extensions")
    //id("org.jetbrains.kotlin.android.extensions")
}

android {
    namespace = "com.example.interview1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.interview1"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
    }

    kapt {
        correctErrorTypes = true
    }

//    androidExtensions {
//        experimental = true
//    }
}

dependencies {

    val daggerVersion = "2.50"
    val firebaseVersion = "23.4.0"
    val retrofitVersion = "2.9.0"
    val coroutinesVersion = "1.7.1"
    val lifeCycleVersion = "2.2.0-rc03"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    implementation("com.google.dagger:hilt-android:$daggerVersion")   ////for dagger hilt
    kapt("com.google.dagger:hilt-android-compiler:$daggerVersion")    ////for dagger hilt

    ////////// retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    ////////// coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    kapt ("androidx.lifecycle:lifecycle-compiler:2.7.0")

    /////////////////glide for load image
    implementation("com.github.bumptech.glide:glide:4.16.0")
}