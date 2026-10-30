# Signal Reporting (Android)

Offline-first field reporting app for Signal Technologies. Reports and images live on the device in Room (`signal_reporting.db`); server sync is future work. See [AGENTS.md](./AGENTS.md) for module layout and agent rules.

## Clone

```bash
git clone https://github.com/noxylem/signal-reporting.git ~/Projects/signal-reporting
cd ~/Projects/signal-reporting
```

## Open in Android Studio

**File → Open** → choose the **repository root** (the folder that contains `settings.gradle.kts` and `app/`), not a parent folder. Let Gradle sync.

## Build and install

From the repo root:

```bash
./gradlew assembleDebug
```

APK: `app/build/outputs/apk/debug/app-debug.apk`

Install on a connected device or emulator:

```bash
./gradlew installDebug
```

Or use the helper:

```bash
chmod +x scripts/install-debug.sh
./scripts/install-debug.sh
```

## Fedora + JDK 21 (Temurin recommended)

Gradle **8.10** cannot run on **Java 25**. If `./gradlew` fails with `What went wrong: 25.0.x`, your shell is using JDK 25 as `JAVA_HOME` or default `java`.

### One-time setup (Temurin 21 on Fedora)

```bash
sudo dnf install -y adoptium-temurin-java-repository temurin-21-jdk
export JAVA_HOME=/usr/lib/jvm/temurin-21-jdk
export PATH="$JAVA_HOME/bin:$PATH"
java -version   # should show 21.x
```

Alternative: `sudo dnf install java-21-openjdk-devel` and set `JAVA_HOME=/usr/lib/jvm/java-21-openjdk`.

Add the `export` lines to `~/.bashrc` if you build often.

### Every build

```bash
export JAVA_HOME=/usr/lib/jvm/temurin-21-jdk   # or java-21-openjdk
export PATH="$JAVA_HOME/bin:$PATH"
./gradlew installDebug
```

### Full Fedora script

From the repo root:

```bash
bash scripts/setup-fedora-build.sh
```

Installs Temurin 21 if needed, clones/updates this repo (when run on jlaptop), and runs `installDebug`.

### Toolchains

The project uses Gradle **JVM toolchains** (JDK 17) with the [Foojay resolver](https://github.com/gradle/foojay-toolchains). **The Gradle daemon** still must start on JDK 17–21 — set `JAVA_HOME` as above.

You need the Android SDK (`ANDROID_HOME` or `local.properties` with `sdk.dir=...`). Android Studio sets this automatically.

## Verify

After login with a Tech ID, Home shows **Build: scaffold-2** at the bottom. **Create New Report** opens Site Info when logged in; otherwise a message asks you to log in first.

## History

Extracted from [noxylem/Chat](https://github.com/noxylem/Chat) branch `signal-reporting-android-scaffold` (nested `signal-reporting-android/`).
