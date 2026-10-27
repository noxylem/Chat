package com.noxylem.signalreporting

import android.app.Application
import com.noxylem.signalreporting.data.local.SignalDatabase
import com.noxylem.signalreporting.data.prefs.TechIdPreferences
import com.noxylem.signalreporting.data.repository.LocalReportRepository
import com.noxylem.signalreporting.data.sync.StubReportSyncService

class SignalReportingApp : Application() {
    lateinit var database: SignalDatabase
        private set

    lateinit var localReportRepository: LocalReportRepository
        private set

    lateinit var techIdPreferences: TechIdPreferences
        private set

    val reportSyncService = StubReportSyncService

    override fun onCreate() {
        super.onCreate()
        database = SignalDatabase.create(this)
        localReportRepository = LocalReportRepository(
            reportDao = database.reportDao(),
            imageSetDao = database.imageSetDao(),
            imageAssetDao = database.imageAssetDao(),
        )
        techIdPreferences = TechIdPreferences(this)
    }
}
