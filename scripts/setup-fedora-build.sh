#!/usr/bin/env bash
# One-shot setup for Signal Reporting on Fedora when system Java is 25.
# Run from repo root: bash scripts/setup-fedora-build.sh
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"

echo "=== Signal Reporting — Fedora build setup ==="

if [[ "${EUID:-$(id -u)}" -eq 0 ]]; then
  echo "Do not run this whole script as root. Run as your normal user; sudo will ask when needed."
  exit 1
fi

echo "Step 1: Install Temurin JDK 21 (needs sudo password)..."
sudo dnf install -y adoptium-temurin-java-repository temurin-21-jdk

JAVA_HOME_CANDIDATES=(
  /usr/lib/jvm/temurin-21-jdk
  /usr/lib/jvm/java-21-openjdk
)

JAVA_HOME=""
for d in "${JAVA_HOME_CANDIDATES[@]}"; do
  if [[ -x "$d/bin/java" ]]; then
    JAVA_HOME="$d"
    break
  fi
done

if [[ -z "$JAVA_HOME" ]]; then
  echo "Could not find JDK 21 under /usr/lib/jvm/. Run: ls /usr/lib/jvm/"
  exit 1
fi

export JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"

echo "Step 2: Using Java:"
java -version

echo "Step 3: Building and installing on connected phone (USB debugging on)..."
./gradlew --stop
./gradlew -Dorg.gradle.java.home="$JAVA_HOME" installDebug

echo ""
echo "=== Done ==="
echo "Add these lines to ~/.bashrc so future terminals use JDK 21:"
echo "  export JAVA_HOME=$JAVA_HOME"
echo "  export PATH=\"\$JAVA_HOME/bin:\$PATH\""
echo ""
echo "Open the app on your phone → Login with a Tech ID → Create New Report."
