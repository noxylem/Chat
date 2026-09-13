#!/usr/bin/env bash
# Run ON jpc (Pop!_OS) — Moonlight CLIENT
# Streams FROM Sunshine on your Fedora laptop TO this machine.
set -euo pipefail

LAPTOP_HOST="${1:-james-laptop.wyrm-pike.ts.net}"

echo "=== 1. Install Moonlight (if missing) ==="
if command -v moonlight >/dev/null 2>&1; then
	echo "moonlight CLI already installed"
elif flatpak list 2>/dev/null | grep -q com.moonlight_stream.Moonlight; then
	echo "Moonlight Flatpak already installed"
else
	echo "Installing moonlight-qt via apt..."
	sudo apt update
	sudo apt install -y moonlight-qt
fi

echo ""
echo "=== 2. Tailscale reachability to laptop (Sunshine) ==="
echo "Laptop hostname: ${LAPTOP_HOST}"
if tailscale ping -c 1 "${LAPTOP_HOST%%.*}" 2>/dev/null | grep -q pong; then
	echo "Tailscale ping: OK"
else
	echo "WARN: Could not ping laptop — is it online and on Tailscale?"
fi

echo ""
echo "=== 3. Test Sunshine ports through Tailscale (from jpc) ==="
for port in 47984 47989 47990; do
	printf "TCP %-5s " "${port}"
	timeout 3 bash -c "echo >/dev/tcp/${LAPTOP_HOST}/${port}" 2>/dev/null && echo open || echo closed
done

echo ""
echo "=== 4. Moonlight power note ==="
echo "Moonlight is a normal app — it uses no extra power until YOU open it."
echo "There is no background service to enable on jpc."

echo ""
echo "=== 5. Pair and connect ==="
echo "1) On the FEDORA LAPTOP first: run ~/moonlight/setup-laptop-sunshine.sh (or finish Sunshine setup)"
echo "2) Open Sunshine web UI on the laptop: https://localhost:47990"
echo "3) On jpc, launch Moonlight:"
echo "     moonlight-qt"
echo "   or: flatpak run com.moonlight_stream.Moonlight"
echo "4) Add PC manually → host: ${LAPTOP_HOST}"
echo "5) Enter the PIN shown in Moonlight into the Sunshine web UI to pair"
echo ""
echo "Optional CLI pair/list:"
echo "  moonlight list ${LAPTOP_HOST}"
echo "  moonlight pair ${LAPTOP_HOST}"
