package com.noxylem.signalreporting.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.techIdDataStore: DataStore<Preferences> by preferencesDataStore(name = "tech_session")

class TechIdPreferences(private val context: Context) {
    private val techIdKey = stringPreferencesKey("tech_id")

    val techId: Flow<String?> = context.techIdDataStore.data.map { prefs ->
        prefs[techIdKey]
    }

    suspend fun saveTechId(value: String) {
        context.techIdDataStore.edit { prefs ->
            prefs[techIdKey] = value.trim()
        }
    }

    suspend fun clearTechId() {
        context.techIdDataStore.edit { prefs ->
            prefs.remove(techIdKey)
        }
    }
}
