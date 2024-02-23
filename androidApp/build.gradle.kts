plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization") version libs.kotlin-version
}

android {
    namespace = "com.plcoding.translator_kmm.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.plcoding.translator_kmm.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.compose-version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.compose-ui)
    implementation(libs.compose-ui-tooling)
    implementation(libs.compose-ui-tooling-preview)
    implementation(libs.compose-foundation)
    implementation(libs.compose-material3)
    implementation(libs.androidx-activity-compose)
    implementation(libs.compose-icons-extended)
    implementation(libs.compos-navigation)
    implementation(libs.coilCompose)

    implementation(libs.hilt-android)
    kapt(libs.hilt-android-compiler)
    kapt(libs.hilt-compiler)
    implementation(libs.hilt-navigation-compose)

    implementation(libs.ktor-android)

    androidTestImplementation(libs.test-runner)
    androidTestImplementation(libs.jUnit)
    androidTestImplementation(libs.compose-testing)
    debugImplementation(libs.compose-test-manifest)

    kaptAndroidTest(libs.hilt-android-compiler)
    androidTestImplementation(libs.hilt-testing)
}