package com.noxylem.signalreporting.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.noxylem.signalreporting.ui.AppViewModelFactory
import com.noxylem.signalreporting.ui.components.PlaceholderLabel

@Composable
fun LoginScreen(
    factory: AppViewModelFactory,
    onLoggedIn: () -> Unit,
) {
    val viewModel: LoginViewModel = viewModel(factory = factory)
    val savedTechId by viewModel.savedTechId.collectAsState()
    var techIdInput by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Signal\nTechnologies\n(logo)",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp),
        )
        PlaceholderLabel("Tech ID form fill box")
        OutlinedTextField(
            value = techIdInput.ifEmpty { savedTechId ?: "" },
            onValueChange = { techIdInput = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            label = { Text("Tech ID") },
            singleLine = true,
        )
        PlaceholderLabel(
            "The Tech ID will point the system to the correct database to sync with the phone, " +
                "and append each tech to each report created",
            modifier = Modifier.padding(vertical = 8.dp),
        )
        Button(
            onClick = {
                val id = techIdInput.ifEmpty { savedTechId ?: "" }
                if (id.isNotBlank()) {
                    viewModel.login(id, onLoggedIn)
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Login Button")
        }
    }
}
