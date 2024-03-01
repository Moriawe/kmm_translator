package com.moriawe.translationapp.core.presentation

import com.moriawe.translationapp.core.domain.language.Language

expect class UiLanguage {
    val language: Language
    companion object {
        fun byCode(langCode: String): UiLanguage
        val allLanguage: List<UiLanguage>
    }

}