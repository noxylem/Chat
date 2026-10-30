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
fun ImportImageScreen(
    imageSetId: Long,
    onBack: () -> Unit,
) {
    AppScaffold(title = "Import image", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            PlaceholderLabel("Import Image")
            PlaceholderLabel("Goes to Android file system folder that is designated in settings")
            Text(text = "Import placeholder — image set $imageSetId")
        }
    }
}
