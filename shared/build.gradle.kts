plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization") version libs.kotlin-version
    id("com.squareup.sqldelight")
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.ktor-core)
                implementation(libs.ktor-serialization)
                implementation(libs.ktor-serializationJson)
                implementation(libs.sql-delight-runtime)
                implementation(libs.sql-delight-coroutines-extensions)
                implementation(libs.kotlin-date-time)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.assertk)
                implementation(libs.turbine)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor-android)
                implementation(libs.sql-delight-android-driver)
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(libs.ktor-ios)
                implementation(libs.sql-delight-native-driver)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.plcoding.translator_kmm"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}