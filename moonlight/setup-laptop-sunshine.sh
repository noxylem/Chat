#!/usr/bin/env bash
# Run ON the Fedora laptop — Sunshine HOST (streams games to Moonlight on jpc)
set -euo pipefail

echo "=== 1. Install Sunshine (Fedora — RPM via COPR, not Flatpak) ==="
if command -v sunshine >/dev/null 2>&1; then
	echo "Sunshine already installed: $(command -v sunshine)"
else
	sudo dnf copr enable -y lizardbyte/beta
	sudo dnf install -y Sunshine
fi

echo ""
echo "=== 2. Firewall (firewalld) — Sunshine ports ==="
if systemctl is-active --quiet firewalld; then
	sudo firewall-cmd --permanent --add-port=47984-47990/tcp
	sudo firewall-cmd --permanent --add-port=47998-48010/udp
	sudo firewall-cmd --permanent --add-port=48100-48110/udp
	sudo firewall-cmd --reload
	echo "firewalld rules added"
else
	echo "firewalld not active — skip (or use ufw/nft if you use those)"
fi

echo ""
echo "=== 3. Sunshine user service (starts on login, idle until someone streams) ==="
mkdir -p ~/.config/systemd/user
if [[ -f /lib/systemd/user/sunshine.service ]]; then
	systemctl --user enable --now sunshine.service
	echo "Enabled distro sunshine.service"
else
	cat >~/.config/systemd/user/sunshine.service <<'EOF'
[Unit]
Description=Sunshine game stream host (Moonlight)
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
	echo "Enabled ~/.config/systemd/user/sunshine.service"
fi

echo ""
echo "=== 4. Power / availability ==="
echo "Sunshine idle uses little CPU/GPU — encoding only starts when Moonlight connects."
echo "This service runs while you are LOGGED IN to the laptop."
echo "If the laptop sleeps or is powered off, streaming will not work."
echo ""
echo "To keep Sunshine running after logout (uses more power — optional):"
echo "  sudo loginctl enable-linger \$USER"

echo ""
echo "=== 5. First-time setup in browser (on the laptop) ==="
echo "Open: https://localhost:47990"
echo "Create login, then pair Moonlight on jpc (PIN from Moonlight → Sunshine UI)"
echo ""
echo "Tailscale IP for Moonlight on jpc to connect:"
tailscale ip -4 2>/dev/null || true
echo "MagicDNS: $(tailscale status --json 2>/dev/null | grep -o '"DNSName":"[^"]*' | head -1 | cut -d'"' -f4 || hostname).$(tailscale status --self 2>/dev/null | awk '{print $NF}' || echo 'wyrm-pike.ts.net')"

systemctl --user --no-pager status sunshine.service 2>/dev/null | head -5 || true
