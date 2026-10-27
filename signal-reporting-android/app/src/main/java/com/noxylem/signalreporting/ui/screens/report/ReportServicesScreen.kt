package com.noxylem.signalreporting.ui.screens.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noxylem.signalreporting.ui.AppViewModelFactory
import com.noxylem.signalreporting.ui.components.AppScaffold
import com.noxylem.signalreporting.ui.components.PlaceholderLabel

@Composable
fun ReportServicesScreen(
    reportId: Long,
    factory: AppViewModelFactory,
    onBack: () -> Unit,
    onNext: () -> Unit,
    onOpenImageSet: (Long) -> Unit,
) {
    val viewModel: ReportWizardViewModel = viewModel(factory = factory)

    AppScaffold(title = "Services & narrative", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PlaceholderLabel(
                "Explanation of services\n[dropbox with boilerplate options from surfaces worked on]\n\n" +
                    "[large scrollable text area]",
            )
            OutlinedButton(
                onClick = { viewModel.beginImageSet(reportId, onOpenImageSet) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                PlaceholderLabel("Begin Image Set")
            }
            OutlinedButton(
                onClick = { viewModel.appendImageSet(reportId, onOpenImageSet) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                PlaceholderLabel("Append Image Set")
            }
            Button(onClick = onNext, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                PlaceholderLabel("Continue to Signature")
            }
        }
    }
}
