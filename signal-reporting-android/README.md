# Signal Reporting (Android)

Offline-first field reporting app for Signal Technologies. See [AGENTS.md](./AGENTS.md) for module layout and agent rules.

## Build (any OS)

From this directory (`signal-reporting-android/`):

```bash
./gradlew assembleDebug
```

APK output: `app/build/outputs/apk/debug/app-debug.apk`

Install on a connected device or emulator:

```bash
./gradlew installDebug
```

Or use the helper script (see below).

Open this folder in Android Studio (not the Chat repo root) and sync Gradle.

## Fedora terminal builds

Gradle **8.10** (this project) cannot run on **Java 25**. If `./gradlew` fails with a vague error like `What went wrong: 25.0.4.1`, your shell is using JDK 25 as `JAVA_HOME` or as the default `java` on `PATH`.

### One-time setup

```bash
sudo dnf install java-21-openjdk-devel
```

Find the JDK path (usually one of these):

```bash
ls /usr/lib/jvm/java-21-openjdk*
```

### Every terminal session (recommended)

Point Gradle at Java 21 **before** running `./gradlew`:

```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
export PATH="$JAVA_HOME/bin:$PATH"
java -version   # should show 21.x, not 25.x
./gradlew assembleDebug
```

Add the `export` lines to `~/.bashrc` if you build often.

### Helper script

From `signal-reporting-android/`:

```bash
chmod +x scripts/install-debug.sh
./scripts/install-debug.sh
```

The script picks Java 21 if installed, then runs `./gradlew installDebug`.

### Toolchains (compile JDK)

This project uses Gradle **JVM toolchains** (JDK 17) with the [Foojay resolver](https://github.com/gradle/foojay-toolchains) so Kotlin/Java compilation can use a downloaded JDK even when your system default is newer. **The Gradle daemon itself** still must start on JDK 17–21 — set `JAVA_HOME` as above.

You also need the Android SDK (`ANDROID_HOME` or `local.properties` with `sdk.dir=...`). Android Studio sets this automatically; on Fedora CLI-only installs, use [command-line tools](https://developer.android.com/studio#command-tools).

## Verify install

After login with a Tech ID, Home shows **Build: scaffold-2** at the bottom. **Create New Report** opens Site Info when logged in; otherwise a message asks you to log in first.
