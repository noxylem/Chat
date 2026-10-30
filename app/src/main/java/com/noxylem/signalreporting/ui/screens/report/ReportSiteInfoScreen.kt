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
fun ReportSiteInfoScreen(
    reportId: Long,
    onBack: () -> Unit,
    onNext: () -> Unit,
) {
    AppScaffold(title = "Customer & site", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            PlaceholderLabel("Customer and Site Identification")
            PlaceholderLabel("What is the customer company?\n__________")
            PlaceholderLabel(
                "What address are you working in? Is there a floor number or other identifying information?\n___________",
            )
            PlaceholderLabel("Date (override)\n__________")
            PlaceholderLabel("Site Contact\n_________")
            PlaceholderLabel("Site Contact Number\n_________")
            Text(
                text = "Report ID (local): $reportId",
                modifier = Modifier.padding(vertical = 8.dp),
            )
            Button(onClick = onNext, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                Text("Continue to Work")
            }
        }
    }
}
