#!/usr/bin/env bash
# Run ON Fedora laptop — Moonlight CLIENT (views jpc's stream)
set -euo pipefail

JPC_HOST="${1:-jpc.wyrm-pike.ts.net}"

echo "=== 1. Install Moonlight (Fedora) ==="
if flatpak list 2>/dev/null | grep -q com.moonlight_stream.Moonlight; then
	echo "Moonlight Flatpak already installed"
else
	flatpak install -y flathub com.moonlight_stream.Moonlight
fi

echo ""
echo "=== 2. Tailscale reachability to jpc (Sunshine host) ==="
echo "jpc hostname: ${JPC_HOST}"
if tailscale ping -c 1 jpc 2>/dev/null | grep -q pong; then
	echo "Tailscale ping: OK"
else
	echo "WARN: Could not ping jpc — is it online and on Tailscale?"
fi

echo ""
echo "=== 3. Test Sunshine ports on jpc ==="
for port in 47984 47989 47990; do
	printf "TCP %-5s " "${port}"
	timeout 3 bash -c "echo >/dev/tcp/${JPC_HOST}/${port}" 2>/dev/null && echo open || echo closed
done

echo ""
echo "=== 4. Power note ==="
echo "Moonlight uses no power until YOU open it. No background service needed."

echo ""
echo "=== 5. Pair and connect ==="
echo "1) On jpc first: run setup-jpc-sunshine.sh and open https://localhost:47990"
echo "2) Launch Moonlight on this laptop:"
echo "     flatpak run com.moonlight_stream.Moonlight"
echo "3) Add PC manually → host: ${JPC_HOST}"
echo "4) Enter the PIN from Moonlight into Sunshine on jpc (https://localhost:47990 on jpc)"
echo "5) Connect and stream jpc's desktop"
