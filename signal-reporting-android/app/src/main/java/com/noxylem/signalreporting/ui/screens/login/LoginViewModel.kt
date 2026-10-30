package com.noxylem.signalreporting.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noxylem.signalreporting.data.prefs.TechIdPreferences
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(
    private val techIdPreferences: TechIdPreferences,
) : ViewModel() {
    val savedTechId: StateFlow<String?> =
        techIdPreferences.techId.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun login(techId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            techIdPreferences.saveTechId(techId)
            onSuccess()
        }
    }
}
