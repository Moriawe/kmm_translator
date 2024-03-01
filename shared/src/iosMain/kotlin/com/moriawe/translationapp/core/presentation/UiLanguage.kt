package com.moriawe.translationapp.core.presentation

import com.moriawe.translationapp.core.domain.language.Language

class UiLanguage(
    actual val language: Language,
    val imageName: String
)  {
    actual companion object {
        actual fun byCode(langCode: String): UiLanguage {
            return allLanguage.find { it.language.langCode == langCode }
                ?: throw IllegalArgumentException("Invalid or unsupported language code")
        }
        actual val allLanguage: List<UiLanguage>
            get() = Language.values().map { language ->
                UiLanguage(
                    language = language,
                    imageName = language.langName.lowercase()
                )
            }
    }
}