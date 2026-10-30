package com.noxylem.signalreporting.ui.screens.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noxylem.signalreporting.ui.components.AppScaffold
import com.noxylem.signalreporting.ui.components.PlaceholderLabel

@Composable
fun SignatureCaptureScreen(
    onBack: () -> Unit,
    onDone: () -> Unit,
) {
    AppScaffold(title = "Sign (landscape)", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            Text(
                text = "Rotate device to landscape for signature capture (UI hint — not enforced yet).",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp),
            )
            PlaceholderLabel("Signature field (flips automatically to landscape)")
            Button(onClick = onDone, modifier = Modifier.fillMaxWidth().padding(top = 24.dp)) {
                Text("Save signature (stub)")
            }
        }
    }
}
