package com.noxylem.signalreporting.ui.screens.report

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
fun ReportDatabaseScreen(onBack: () -> Unit) {
    AppScaffold(title = "Report database", onBack = onBack) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            PlaceholderLabel("Database")
            PlaceholderLabel("Search Button magnifying glass and sort hamburger")
            Text(
                text = """
                    Completed Reports
                    - October 2026 [+]
                    - September 2026 [-]
                       - 30/09/2026 SGI 151 Yonge St
                    Incomplete Reports
                    - October 2026 [+]
                    Unassigned Image Sets
                     - October 2026 [-]
                         - [dd/mm/yyyy] Palmark
                    Other
                     - for messing around and other stuff
                """.trimIndent(),
                modifier = Modifier.padding(top = 16.dp),
            )
            PlaceholderLabel(
                "(Expandable tree stub — search, PDF-first ordering, revisions, green check / clock icons coming later)",
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }
}
