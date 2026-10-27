package com.noxylem.signalreporting.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noxylem.signalreporting.ui.AppViewModelFactory
import com.noxylem.signalreporting.ui.components.AppScaffold
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    factory: AppViewModelFactory,
    onCreateNewReport: (Long) -> Unit,
    onOpenReportDatabase: () -> Unit,
    onCreateImageSet: (Long) -> Unit,
) {
    val viewModel: HomeViewModel = viewModel(factory = factory)
    val scope = rememberCoroutineScope()

    AppScaffold(title = "Home", onBack = null) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    scope.launch {
                        val reportId = viewModel.createNewReport()
                        if (reportId != null) {
                            onCreateNewReport(reportId)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Create New Report")
            }
            Button(
                onClick = onOpenReportDatabase,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Report Database")
            }
            Button(
                onClick = { viewModel.createStandaloneImageSet(onCreateImageSet) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Create Image Set")
            }
        }
    }
}
