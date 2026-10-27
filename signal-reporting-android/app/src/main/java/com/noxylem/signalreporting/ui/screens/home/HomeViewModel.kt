package com.noxylem.signalreporting.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noxylem.signalreporting.data.prefs.TechIdPreferences
import com.noxylem.signalreporting.data.repository.LocalReportRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: LocalReportRepository,
    techIdPreferences: TechIdPreferences,
) : ViewModel() {
    val techId: StateFlow<String?> =
        techIdPreferences.techId.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    suspend fun createNewReport(): Long? {
        val techId = techId.value?.takeIf { it.isNotBlank() } ?: return null
        return repository.createDraftReport(techId)
    }

    fun createStandaloneImageSet(onCreated: (Long) -> Unit) {
        viewModelScope.launch {
            val id = repository.createImageSet(title = "Image set ${System.currentTimeMillis()}")
            onCreated(id)
        }
    }
}
