package com.example.people.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingsViewModel: ViewModel() {
    private val _dark = mutableStateOf(false)
    val dark = _dark

    fun setDark(newVal: Boolean) {
        _dark.value = newVal
    }
}