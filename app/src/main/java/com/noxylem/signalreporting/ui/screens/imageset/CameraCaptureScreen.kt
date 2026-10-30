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
fun CameraCaptureScreen(
    imageSetId: Long,
    onBack: () -> Unit,
) {
    AppScaffold(title = "Take image", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            PlaceholderLabel("Take new image")
            PlaceholderLabel("camera button")
            PlaceholderLabel(
                "Name\n________ (Location 1+n auto)\n\nType of Work Surface\n[dropbox with checkmarks]\n\n" +
                    "Notes (text box)\n\nLimitations and Recommendations\n[dropbox with checkmarks]",
            )
            Text(text = "Camera placeholder — image set $imageSetId")
        }
    }
}
