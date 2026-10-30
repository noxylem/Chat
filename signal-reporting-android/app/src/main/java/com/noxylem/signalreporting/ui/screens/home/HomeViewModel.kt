package com.noxylem.signalreporting.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noxylem.signalreporting.data.prefs.TechIdPreferences
import com.noxylem.signalreporting.data.repository.LocalReportRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: LocalReportRepository,
    private val techIdPreferences: TechIdPreferences,
) : ViewModel() {

    suspend fun createNewReport(): Long? {
        val techId = techIdPreferences.techId.first()?.takeIf { it.isNotBlank() } ?: return null
        return repository.createDraftReport(techId)
    }

    fun createStandaloneImageSet(onCreated: (Long) -> Unit) {
        viewModelScope.launch {
            val id = repository.createImageSet(title = "Image set ${System.currentTimeMillis()}")
            onCreated(id)
        }
    }
}
