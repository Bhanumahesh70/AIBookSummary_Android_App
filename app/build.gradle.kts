plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Google Services plugin)
}

android {
    namespace = "com.example.aibooksummaryapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.aibooksummaryapp"
        minSdk = 34
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.crashlytics.buildtools)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.room.runtime)
    implementation(libs.firebase.auth)
    implementation(libs.play.services)
    implementation(libs.credentials)
    implementation(libs.googleId)
    implementation(libs.firebase.database)
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)
    implementation(libs.openai.retrofit)
    implementation(libs.openai.gson)
}
