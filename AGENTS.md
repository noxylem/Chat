# Signal Reporting Android

Offline-first field reporting app for Signal Technologies. **Every report and image set is stored locally on the phone** in Room (`signal_reporting.db`). Server sync is future work — only `ReportSyncService` / `StubReportSyncService` exists today.

## Stack

- Kotlin, Jetpack Compose, Material 3
- Navigation Compose (routes in `navigation/Routes.kt`; wireflow map lives in the Cursor Project **Context** doc `docs/navigation-map.md`, not in this repo)
- Room + KSP, ViewModel, DataStore (Tech ID session)
- minSdk 26, targetSdk 35

## Module layout (`:app`)

```
com.noxylem.signalreporting/
  data/
    local/          # Room entities, DAOs, SignalDatabase
    repository/     # LocalReportRepository — local reads/writes only
    prefs/          # TechIdPreferences (DataStore)
    sync/           # ReportSyncService stub
  navigation/       # Routes, AppNavHost
  ui/
    screens/        # One package per flow (login, home, report, imageset)
    components/     # AppScaffold, shared UI
    theme/
```

## Rules for agents

1. **Offline-first:** Do not add network calls without going through a sync layer and keeping Room as source of truth while in the field.
2. **No business logic in Composables** — use ViewModels and `LocalReportRepository`.
3. **Match route ids** to the Project Context navigation map when adding screens.
4. **Photos:** When implementing camera/import, write files to app-private storage, then persist `ImageAssetEntity` rows (path or `contentUri`).
5. **Never commit** `local.properties`, keystores, or secrets.

## Build

From the **repository root**:

```bash
./gradlew assembleDebug
```

On **Fedora** with Java 25 as default, set `JAVA_HOME` to JDK 21 (Temurin or OpenJDK) — see [README.md](./README.md#fedora--jdk-21-temurin-recommended).

Open the repository root in Android Studio and sync Gradle.

## Scaffold status

Placeholder UI labels follow the Excalidraw wireflow. Report database tree, camera, PDF, search, and real sync are not implemented yet.
