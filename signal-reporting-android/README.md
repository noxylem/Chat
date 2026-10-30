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

Open this folder in Android Studio (not the Chat repo root) and sync Gradle.

## Fedora + Java 25

On recent Fedora releases, the default `java` package may be **Java 25**, and Android Studio’s bundled **JBR** may also be **Java 25**. This project uses **Gradle 8.10.2**, which supports running Gradle on **JVM 21–23** only—not Java 25.

If `./gradlew` fails with a vague error like `What went wrong: 25.0.4.1`, Gradle is starting on JDK 25. **Do not point Gradle at Java 25.** Use JDK 21 (recommended) or 23 for the Gradle daemon and Android Gradle Plugin.

Kotlin/Java compilation in this project targets **JDK 17** via Gradle toolchains; that does not change the JVM Gradle itself must use.

### Install Temurin 21 (recommended on Fedora)

When `java-21-openjdk-devel` is unavailable on your Fedora version, use Eclipse Temurin:

```bash
sudo dnf install -y adoptium-temurin-java-repository
sudo dnf install -y temurin-21-jdk
export JAVA_HOME=/usr/lib/jvm/temurin-21-jdk
./gradlew -Dorg.gradle.java.home="$JAVA_HOME" installDebug
```

Keep `JAVA_HOME` set in any shell where you run `./gradlew`, or pass `-Dorg.gradle.java.home=...` every time.

You still need the Android SDK (`ANDROID_HOME` or `local.properties` with `sdk.dir=...`). Android Studio sets this automatically; on Fedora CLI-only installs, use [command-line tools](https://developer.android.com/studio#command-tools).

### Helper script (Fedora)

From `signal-reporting-android/`:

```bash
./scripts/install-debug-fedora.sh
```

The script tries `/usr/lib/jvm/temurin-21-jdk`, then `java-23-openjdk`, then `java-21-openjdk`, runs `./gradlew --stop`, then `installDebug` with `-Dorg.gradle.java.home`.

A generic helper that adjusts `JAVA_HOME`/`PATH` for Java 21 is also available: `./scripts/install-debug.sh`.

## Verify install

After login with a Tech ID, Home shows **Build: scaffold-2** at the bottom. **Create New Report** opens Site Info when logged in; otherwise a message asks you to log in first.
