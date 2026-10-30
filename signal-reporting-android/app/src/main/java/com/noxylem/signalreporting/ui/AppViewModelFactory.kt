package com.noxylem.signalreporting.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.noxylem.signalreporting.SignalReportingApp
import com.noxylem.signalreporting.ui.screens.home.HomeViewModel
import com.noxylem.signalreporting.ui.screens.login.LoginViewModel
import com.noxylem.signalreporting.ui.screens.report.ReportWizardViewModel

class AppViewModelFactory(
    private val app: SignalReportingApp,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(app.techIdPreferences) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) ->
                HomeViewModel(app.localReportRepository, app.techIdPreferences) as T
            modelClass.isAssignableFrom(ReportWizardViewModel::class.java) ->
                ReportWizardViewModel(app.localReportRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
        }
    }
}
