plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.movieonlineapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.movieonlineapp"
        minSdk = 24
        targetSdk = 34
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
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation(libs.androidx.media3.exoplayer.v100)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.exoplayer.v110)
    implementation(libs.androidx.media3.exoplayer.hls)
    implementation(libs.glide.v4110)
    // sử dụng phiên bản okhttp3
    implementation(libs.okhttp)
    // bóc tách và chuyển đổi JSON với Gson
    implementation(libs.gson)
    // thư viện Retrofit2
    implementation(libs.retrofit)
    // thư viện converter của Retrofit cho Gson
    implementation(libs.converter.gson)
    // room database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    // use coroutine
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.circleimageview)
    implementation(libs.glide)
    implementation(libs.jackson.core)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.annotations)
    implementation(libs.viewpager2)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(kotlin("script-runtime"))
}