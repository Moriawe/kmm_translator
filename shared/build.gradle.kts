plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    //alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqlDelightGradlePlugin)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach {
//        it.binaries.framework {
//            baseName = "shared"
//            isStatic = true
//        }
//    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.core)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.sql.delight.runtime)
            implementation(libs.sql.delight.coroutines.extensions)
            implementation(libs.kotlin.date.time)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.assertk)
            implementation(libs.turbine)
        }
        androidMain.dependencies {
            implementation(libs.ktor.android)
            implementation(libs.sql.delight.android.driver)
        }
    }
}

android {
    namespace = "com.moriawe.testapp"
    compileSdk = 34
    defaultConfig {
        minSdk = 28
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

sqldelight {
    database("TranslateDatabase") {
        //packageName = "com.plcoding.translator_kmm.database"
        packageName = "com.moriawe.translationapp.database"
        sourceFolders = listOf("sqldelight")
    }
}