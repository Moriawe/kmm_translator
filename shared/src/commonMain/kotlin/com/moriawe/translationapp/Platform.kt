package com.moriawe.translationapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform