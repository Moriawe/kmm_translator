package com.moriawe.translationapp.translate.presentation

import com.moriawe.translationapp.core.domain.util.Resource
import com.moriawe.translationapp.core.domain.util.toCommonStateFlow
import com.moriawe.translationapp.core.presentation.UiLanguage
import com.moriawe.translationapp.translate.domain.history.HistoryDataSource
import com.moriawe.translationapp.translate.domain.translate.Translate
import com.moriawe.translationapp.translate.domain.translate.TranslateException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch

class TranslateViewModel(
    private val translate: Translate,
    private val historyDataSource: HistoryDataSource,
    private val coroutineScope: CoroutineScope?
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(TranslateState())
    val state = combine(
        _state,
        historyDataSource.getHistory()
    ) { state, history ->
        if (state.history != history) {
            state.copy(
                history = history.mapNotNull { item ->
                    UiHistoryItem(
                        id = item.id ?: return@mapNotNull null,
                        fromText = item.fromText,
                        toText = item.toText,
                        fromLanguage = UiLanguage.byCode(item.fromLanguageCode),
                        toLanguage = UiLanguage.byCode(item.toLanguageCode)
                    )
                }
            )
        } else state
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), TranslateState())
        .toCommonStateFlow()

    private var translateJob: Job? = null

    fun onEvent(event: TranslateEvent) {
        when(event) {
            is TranslateEvent.ChooseFromLanguage -> {
                _state.update { it.copy(
                    isChoosingFromLanguage = false,
                    fromLanguage = event.language
                ) }
            }
            is TranslateEvent.ChooseToLanguage -> {
                val newState = _state.updateAndGet { it.copy(
                    isChoosingToLanguage = false,
                    toLanguage = event.language
                ) }
                translate(newState)
            }
            is TranslateEvent.StopChoosingLanguage -> {
                _state.update { it.copy(
                    isChoosingFromLanguage = false,
                    isChoosingToLanguage = false
                ) }
            }
            is TranslateEvent.SwapLanguage -> {
                _state.update { it.copy(
                    fromLanguage = it.toLanguage,
                    toLanguage = it.fromLanguage,
                    fromText = it.toText ?: "",
                    toText = if(it.toText != null) it.fromText else null
                ) }
            }
            is TranslateEvent.ChangeTranslationText -> {
                _state.update { it.copy(
                    fromText = event.text
                ) }
            }
            is TranslateEvent.Translate -> {
                translate(state.value)
            }
            is TranslateEvent.OpenFromLanguageDropDown -> {
                _state.update { it.copy(
                    isChoosingFromLanguage = true
                ) }
            }
            is TranslateEvent.OpenToLanguageDropDown -> {
                _state.update { it.copy(
                    isChoosingToLanguage = true
                ) }
            }
            is TranslateEvent.CloseTranslation -> {
                _state.update { it.copy(
                    isTranslating = false,
                    fromText = "",
                    toText = null
                ) }
            }
            is TranslateEvent.SelectHistoryItem -> {
                translateJob?.cancel()
                _state.update { it.copy(
                    fromText = event.item.fromText,
                    toText = event.item.toText,
                    fromLanguage = event.item.fromLanguage,
                    toLanguage = event.item.toLanguage,
                    isTranslating = false
                ) }
            }
            is TranslateEvent.EditTranslation -> {
                if(state.value.toText != null) {
                    _state.update { it.copy(
                        toText = null,
                        isTranslating = false
                    ) }
                }
            }
            is TranslateEvent.SubmitVoiceResult -> {
                _state.update { it.copy(
                    fromText = event.result ?: it.fromText,
                    isTranslating = if(event.result != null) false else it.isTranslating,
                    toText = if(event.result != null) null else it.toText
                ) }
            }
            else -> Unit
        }
    }

    // We send the state here because the update state is a async function and sometimes the state
    // is not updated yet which means isTranslating and fromText is not updated yet resulting in
    // the wrong state being sent to the translate function
    private fun translate(state: TranslateState) {
        if(state.isTranslating || state.fromText.isBlank()) {
            return
        }
        translateJob = viewModelScope.launch {
            _state.update { it.copy(
                isTranslating = true
            ) }
            val result = translate.execute(
                fromLanguage = state.fromLanguage.language,
                fromText = state.fromText,
                toLanguage = state.toLanguage.language
            )
            when(result) {
                is Resource.Success -> {
                    _state.update { it.copy(
                        toText = result.data,
                        isTranslating = false
                    ) }
                }
                is Resource.Error -> {
                    _state.update { it.copy(
                        error = (result.throwable as? TranslateException)?.error,
                        isTranslating = false
                    ) }
                }
            }
        }
    }

}