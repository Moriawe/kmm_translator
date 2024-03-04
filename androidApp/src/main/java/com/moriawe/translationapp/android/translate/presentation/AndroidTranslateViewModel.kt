package com.moriawe.translationapp.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moriawe.translationapp.translate.domain.history.HistoryDataSource
import com.moriawe.translationapp.translate.domain.translate.Translate
import com.moriawe.translationapp.translate.presentation.TranslateEvent
import com.moriawe.translationapp.translate.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translate: Translate,
    private val historyDataSource: HistoryDataSource
): ViewModel() {

    // TODO: How do we use a viewmodel in a viewmodel?
    private val viewModel by lazy {
        TranslateViewModel(
            translate = translate,
            historyDataSource = historyDataSource,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: TranslateEvent) {
        viewModel.onEvent(event)
    }
}