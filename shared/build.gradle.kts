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
    namespace = "com.moriawe.translationapp"
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
        databases {
            create("TranslateDatabase") {
                packageName.set("com.moriawe.translationapp.database")
                //dialect("app.cash.sqldelight:mysql-dialect:2.0.1")
                // Version catalogs also work!
                //dialect(libs.sqldelight.dialects.mysql)

                //sourceFolders = ["sqldelight"]
                //deriveSchemaFromMigrations = true
            }
        }
    }
