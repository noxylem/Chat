package com.noxylem.signalreporting.ui.screens.imageset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noxylem.signalreporting.ui.components.AppScaffold
import com.noxylem.signalreporting.ui.components.PlaceholderLabel

@Composable
fun ImageSetScreen(
    imageSetId: Long,
    reportId: Long?,
    onBack: () -> Unit,
    onTakeImage: () -> Unit,
    onImportImage: () -> Unit,
    onMarkingsStub: () -> Unit,
) {
    AppScaffold(title = "Image set", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            PlaceholderLabel("Create Image Set\n\nImages [+]\n(expands to show folder system stub)")
            PlaceholderLabel("+ expandable menu for options to add images/canvas/pdfs")
            Text(text = "Local image set id: $imageSetId")
            if (reportId != null) {
                Text(text = "Linked report id: $reportId")
            }
            Button(onClick = onTakeImage, modifier = Modifier.fillMaxWidth()) {
                PlaceholderLabel("Take new image / camera button")
            }
            OutlinedButton(onClick = onImportImage, modifier = Modifier.fillMaxWidth()) {
                PlaceholderLabel("Import Image")
            }
            OutlinedButton(onClick = onMarkingsStub, modifier = Modifier.fillMaxWidth()) {
                PlaceholderLabel("Markings (stub)")
            }
        }
    }
}
