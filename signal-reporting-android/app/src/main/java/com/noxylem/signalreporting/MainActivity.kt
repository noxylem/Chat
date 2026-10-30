package com.noxylem.signalreporting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.noxylem.signalreporting.navigation.AppNavHost
import com.noxylem.signalreporting.ui.AppViewModelFactory
import com.noxylem.signalreporting.ui.theme.SignalReportingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as SignalReportingApp
        val factory = AppViewModelFactory(app)
        setContent {
            SignalReportingTheme {
                AppNavHost(factory = factory)
            }
        }
    }
}
