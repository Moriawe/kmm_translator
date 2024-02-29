package com.moriawe.translationapp.translate.data.history

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.moriawe.translationapp.core.domain.util.CommonFlow
import com.moriawe.translationapp.core.domain.util.toCommonFlow
import com.moriawe.translationapp.database.TranslateDatabase
import com.moriawe.translationapp.translate.domain.history.HistoryDataSource
import com.moriawe.translationapp.translate.domain.history.HistoryItem
import kotlinx.coroutines.flow.map

class SqlDelightHistoryDataSource(
    db: TranslateDatabase
): HistoryDataSource {

    private val queries = db.translateQueries
    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return queries
            .getHistory()
            .asFlow()
            .mapToList()
            .map { history ->
                history.map {
                    it.toHistoryItem()
                }
            }
            .toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        TODO("Not yet implemented")
    }
}