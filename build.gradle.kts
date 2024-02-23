buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.kotlinGradlePlugin)
        classpath(libs.androidBuildTools)
        classpath(libs.sqlDelightGradlePlugin)
        classpath(libs.hiltGradlePlugin)
    }
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
