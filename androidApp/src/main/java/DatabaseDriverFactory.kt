package com.moriawe.translationapp.translate.data.local

import app.cash.sqldelight.db.SqlDriver
import com.moriawe.translationapp.database.TranslateDatabase


actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(TranslateDatabase.Schema,  "translate.db")
    }
}