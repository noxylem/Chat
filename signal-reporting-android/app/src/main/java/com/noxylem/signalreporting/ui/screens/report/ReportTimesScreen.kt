package com.noxylem.signalreporting.ui.screens.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noxylem.signalreporting.ui.components.AppScaffold
import com.noxylem.signalreporting.ui.components.PlaceholderLabel

@Composable
fun ReportTimesScreen(
    onBack: () -> Unit,
    onFinish: () -> Unit,
) {
    AppScaffold(title = "Technician & times", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            PlaceholderLabel(
                "Technician\n[autofilled, can be changed by entering tech id]\n\n" +
                    "Log In\n[24 hour clock entry with hours and 15 min intervals]\n" +
                    "On Site\n[same]\nReport Completion\n[same]\nLog Off\n[same]\n" +
                    "Email a copy to signer?\n[checkbox]",
            )
            Button(onClick = onFinish, modifier = Modifier.fillMaxWidth().padding(top = 24.dp)) {
                PlaceholderLabel("Finish report wizard (save draft locally)")
            }
        }
    }
}
