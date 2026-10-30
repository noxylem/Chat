package com.noxylem.signalreporting.ui.screens.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noxylem.signalreporting.ui.components.AppScaffold
import com.noxylem.signalreporting.ui.components.PlaceholderLabel

@Composable
fun ReportSignatureScreen(
    reportId: Long,
    onBack: () -> Unit,
    onOpenCapture: () -> Unit,
    onNext: () -> Unit,
) {
    AppScaffold(title = "Signature", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            PlaceholderLabel(
                "Signature\n\nBy signing this document you are agreeing to the Signal terms and conditions, " +
                    "click below to read\n\n[image of a document file]\n\nSignature Button\n\n" +
                    "Signer Name\n__________\n\nSigner Phone Number\n__________\n\nSigned Email\n__________",
            )
            OutlinedButton(
                onClick = onOpenCapture,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            ) {
                PlaceholderLabel("Signature Button")
            }
            Button(onClick = onNext, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                PlaceholderLabel("Continue to Technician & times")
            }
        }
    }
}
