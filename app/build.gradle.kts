plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "ru.itis.mygarden"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.itis.mygarden"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
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
}

dependencies {

    val room_version = "2.6.1"
    val lifecycle_viewmodel_ktx_version = "2.5.1"

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_viewmodel_ktx_version")

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    kapt("androidx.room:room-compiler:$room_version")

    implementation(libs.play.services.maps)
    val nav_version = "2.7.7"
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    val retrofit2_version = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit2_version")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofit2_version")

    val okhttp3_version = "4.9.0"
    implementation ("com.squareup.okhttp3:logging-interceptor:$okhttp3_version")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}