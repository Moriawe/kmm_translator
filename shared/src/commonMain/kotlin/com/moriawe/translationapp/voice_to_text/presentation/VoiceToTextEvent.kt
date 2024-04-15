package com.moriawe.translationapp.voice_to_text.presentation

sealed class VoiceToTextEvent {
    data object Close: VoiceToTextEvent()
    data class PermissionsResult(
        val isGranted: Boolean,
        val isPermanentlyDeclined: Boolean
    ): VoiceToTextEvent()
    data class ToggleRecording(
        val languageCode:String
    ): VoiceToTextEvent()
    data object Reset: VoiceToTextEvent()
}