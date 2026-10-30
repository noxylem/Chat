package com.noxylem.signalreporting.ui.screens.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noxylem.signalreporting.ui.components.AppScaffold
import com.noxylem.signalreporting.ui.components.PlaceholderLabel

@Composable
fun ReportWorkScreen(
    reportId: Long,
    onBack: () -> Unit,
    onNext: () -> Unit,
) {
    AppScaffold(title = "Work", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            PlaceholderLabel("Work")
            PlaceholderLabel("Reason for scan services\n______________")
            PlaceholderLabel("Were there any additional requirements added by the customer?\n______________")
            PlaceholderLabel("Which services were performed?\n[Dropbox with checkmark list]")
            PlaceholderLabel("What kind of surfaces were worked on?\n[Dropbox with checkmark list and other field]")
            PlaceholderLabel("Are there any recommendations that should be mentioned?\n[Dropbox with checkmark and other field]")
            PlaceholderLabel("Are there any limitations that should be mentioned?\n[Dropbox with checkmark and other field]")
            Button(onClick = onNext, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                Text("Continue to Services & narrative")
            }
        }
    }
}
