#!/usr/bin/env bash
# Install debug APK on a connected device/emulator using Java 21 when available.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

resolve_java21_home() {
  if [[ -n "${JAVA_HOME:-}" ]] && "$JAVA_HOME/bin/java" -version 2>&1 | grep -qE 'version "21'; then
    return 0
  fi
  local candidates=(
    /usr/lib/jvm/temurin-21-jdk
    /usr/lib/jvm/java-21-openjdk
    /usr/lib/jvm/java-21
    /usr/lib/jvm/java-21-openjdk-*
  )
  local dir
  for dir in "${candidates[@]}"; do
    for expanded in $dir; do
      if [[ -x "$expanded/bin/java" ]] && "$expanded/bin/java" -version 2>&1 | grep -qE 'version "21'; then
        export JAVA_HOME="$expanded"
        export PATH="$JAVA_HOME/bin:$PATH"
        return 0
      fi
    done
  done
  echo "Warning: Java 21 not found. Gradle may fail if your default Java is 25+." >&2
  echo "On Fedora: sudo dnf install temurin-21-jdk (or java-21-openjdk-devel)" >&2
  echo "Then: export JAVA_HOME=/usr/lib/jvm/temurin-21-jdk" >&2
  return 1
}

resolve_java21_home || true

echo "Using java: $(command -v java)"
java -version

exec ./gradlew installDebug "$@"
