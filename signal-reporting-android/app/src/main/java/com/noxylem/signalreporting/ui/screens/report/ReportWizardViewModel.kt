package com.noxylem.signalreporting.ui.screens.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noxylem.signalreporting.data.local.entity.ReportEntity
import com.noxylem.signalreporting.data.repository.LocalReportRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReportWizardViewModel(
    private val repository: LocalReportRepository,
) : ViewModel() {
    fun observeReport(reportId: Long): StateFlow<ReportEntity?> =
        repository.observeReport(reportId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun beginImageSet(reportId: Long, onCreated: (Long) -> Unit) {
        viewModelScope.launch {
            val id = repository.createImageSet(
                title = "Report $reportId image set",
                reportId = reportId,
            )
            onCreated(id)
        }
    }

    fun appendImageSet(reportId: Long, onCreated: (Long) -> Unit) {
        viewModelScope.launch {
            val id = repository.createImageSet(
                title = "Append ${System.currentTimeMillis()}",
                reportId = reportId,
            )
            onCreated(id)
        }
    }

    fun touchReport(reportId: Long) {
        viewModelScope.launch {
            repository.getReport(reportId)?.let { repository.updateReport(it) }
        }
    }
}
