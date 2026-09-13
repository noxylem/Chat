#!/usr/bin/env bash
# Run ON jpc (Pop!_OS) — Sunshine HOST (streams jpc's screen to Moonlight clients)
set -euo pipefail

echo "=== 1. Install Sunshine (Pop!_OS / Ubuntu) ==="
if command -v sunshine >/dev/null 2>&1; then
	echo "Sunshine already installed: $(command -v sunshine)"
elif flatpak list 2>/dev/null | grep -q dev.lizardbyte.app.Sunshine; then
	echo "Sunshine Flatpak already installed"
else
	echo "Trying .deb from GitHub (recommended on Pop!_OS)..."
	ARCH="$(dpkg --print-architecture)"
	TMP="$(mktemp -d)"
	cd "${TMP}"
	# Latest release — adjust version if this URL 404s; check github.com/LizardByte/Sunshine/releases
	URL="https://github.com/LizardByte/Sunshine/releases/latest/download/Sunshine-debian-${ARCH}.deb"
	if curl -fsSL -o sunshine.deb "${URL}"; then
		sudo dpkg -i sunshine.deb || sudo apt-get install -f -y
	else
		echo "Deb download failed — install Flatpak instead:"
		echo "  flatpak install flathub dev.lizardbyte.app.Sunshine"
		echo "  flatpak run --command=additional-install.sh dev.lizardbyte.app.Sunshine"
		exit 1
	fi
fi

echo ""
echo "=== 2. Host firewall (if ufw is active) ==="
if command -v ufw >/dev/null 2>&1 && sudo ufw status 2>/dev/null | grep -q "Status: active"; then
	sudo ufw allow 47984:47990/tcp comment 'Sunshine'
	sudo ufw allow 47998:48010/udp comment 'Sunshine'
	sudo ufw allow 48100:48110/udp comment 'Sunshine'
	echo "ufw rules added"
else
	echo "ufw not active — Tailscale ACL is the main gate (good for your setup)"
fi

echo ""
echo "=== 3. Sunshine service (idle until Moonlight connects) ==="
if flatpak list 2>/dev/null | grep -q dev.lizardbyte.app.Sunshine; then
	flatpak run --command=additional-install.sh dev.lizardbyte.app.Sunshine 2>/dev/null || true
	systemctl --user enable --now app-dev.lizardbyte.app.Sunshine.service
	echo "Enabled Flatpak Sunshine user service"
elif [[ -f /lib/systemd/user/sunshine.service ]]; then
	systemctl --user enable --now sunshine.service
	echo "Enabled sunshine.service"
else
	mkdir -p ~/.config/systemd/user
	cat >~/.config/systemd/user/sunshine.service <<'EOF'
[Unit]
Description=Sunshine stream host (Moonlight)
After=graphical-session.target

[Service]
ExecStart=/usr/bin/sunshine
Restart=on-failure
RestartSec=5

[Install]
WantedBy=default.target
EOF
	systemctl --user daemon-reload
	systemctl --user enable --now sunshine.service
fi

echo ""
echo "=== 4. Power note ==="
echo "Sunshine sits idle until someone connects from Moonlight."
echo "GPU/CPU only work hard during an active stream."
echo "jpc needs a logged-in desktop session (or display) for screen capture."

echo ""
echo "=== 5. First-time Sunshine setup ==="
echo "On jpc, open in a browser:"
echo "  https://localhost:47990"
echo "Create login. Pair Moonlight on the laptop (PIN from Moonlight → this page)."
echo ""
echo "Moonlight on laptop should connect to:"
echo "  jpc.wyrm-pike.ts.net"
echo "  (or $(tailscale ip -4 2>/dev/null || echo '100.80.121.50'))"
echo ""
systemctl --user --no-pager status sunshine.service 2>/dev/null | head -5 \
	|| systemctl --user --no-pager status app-dev.lizardbyte.app.Sunshine.service 2>/dev/null | head -5 \
	|| true
