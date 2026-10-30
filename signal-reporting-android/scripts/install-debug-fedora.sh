#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

pick_java_home() {
  local candidate
  for candidate in \
    /usr/lib/jvm/temurin-21-jdk \
    /usr/lib/jvm/java-23-openjdk \
    /usr/lib/jvm/java-21-openjdk
  do
    if [[ -d "$candidate" && -x "$candidate/bin/java" ]]; then
      echo "$candidate"
      return 0
    fi
  done
  return 1
}

if ! JAVA_HOME="$(pick_java_home)"; then
  echo "No suitable JDK found (tried temurin-21-jdk, java-23-openjdk, java-21-openjdk)." >&2
  echo "Install Temurin 21 — see README.md section \"Fedora + Java 25\"." >&2
  exit 1
fi

export JAVA_HOME
echo "Using JAVA_HOME=$JAVA_HOME"

./gradlew --stop 2>/dev/null || true
./gradlew -Dorg.gradle.java.home="$JAVA_HOME" installDebug
