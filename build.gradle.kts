buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.sqlDelightGradlePlugin).apply(false)
    alias(libs.plugins.hiltGradlePlugin).apply(false)
    //alias(libs.plugins.androidBuildTools).apply(false)
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
