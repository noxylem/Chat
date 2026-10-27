package com.noxylem.signalreporting.ui.screens.imageset

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.noxylem.signalreporting.ui.components.AppScaffold
import com.noxylem.signalreporting.ui.components.PlaceholderLabel

@Composable
fun ImageMarkingsScreen(
    imageSetId: Long,
    imageAssetId: Long,
    onBack: () -> Unit,
) {
    AppScaffold(title = "Markings", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            PlaceholderLabel(
                "Markings\n\n[this will be autopopulated with various types of markings in order to select " +
                    "colours and make other notes on the style of markings depending on which kind of work " +
                    "surface is selected on the previous page]",
            )
            Text(text = "Image set $imageSetId — asset $imageAssetId")
            PlaceholderLabel("Previous Image       Next Image (stub)")
        }
    }
}
