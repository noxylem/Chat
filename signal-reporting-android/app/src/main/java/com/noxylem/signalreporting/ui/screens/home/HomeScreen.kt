package com.noxylem.signalreporting.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noxylem.signalreporting.BuildConfig
import com.noxylem.signalreporting.R
import com.noxylem.signalreporting.ui.AppViewModelFactory
import com.noxylem.signalreporting.ui.components.AppScaffold
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    factory: AppViewModelFactory,
    onCreateNewReport: (Long) -> Unit,
    onOpenReportDatabase: () -> Unit,
    onCreateImageSet: (Long) -> Unit,
    onGoToLogin: () -> Unit,
) {
    val viewModel: HomeViewModel = viewModel(factory = factory)
    val savedTechId by viewModel.savedTechId.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val isLoggedIn = !savedTechId.isNullOrBlank()
    val techIdStatus = if (isLoggedIn) {
        stringResource(R.string.home_tech_id_logged_in, savedTechId!!)
    } else {
        stringResource(R.string.home_tech_id_not_logged_in)
    }
    val enterTechIdMessage = stringResource(R.string.snackbar_enter_tech_id_first)
    val draftSavedMessage = stringResource(R.string.snackbar_draft_saved)
    val goToLoginLabel = stringResource(R.string.go_to_login)

    AppScaffold(title = "Home", onBack = null, snackbarHostState = snackbarHostState) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = techIdStatus,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
            if (!isLoggedIn) {
                OutlinedButton(
                    onClick = onGoToLogin,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(goToLoginLabel)
                }
            }
            Button(
                onClick = {
                    scope.launch {
                        val reportId = viewModel.createNewReport()
                        if (reportId != null) {
                            snackbarHostState.showSnackbar(message = draftSavedMessage)
                            onCreateNewReport(reportId)
                        } else {
                            val result = snackbarHostState.showSnackbar(
                                message = enterTechIdMessage,
                                actionLabel = goToLoginLabel,
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                onGoToLogin()
                            }
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
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.build_stamp_label, BuildConfig.BUILD_STAMP),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
