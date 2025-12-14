package io.jadu.todoApp.ui.utils

sealed class UiEvent {
    data class ShowError(val message: String) : UiEvent()
    data class OnSuccess(val message: String) : UiEvent()
}