package com.moriawe.translationapp.translate.domain.translate

import com.moriawe.translationapp.core.domain.language.Language

interface TranslateClient {
    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String
}