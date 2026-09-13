#!/usr/bin/env bash
# Run ON jpc if Sonarr/Radarr still redirect-loop in the browser.
# Fixes config.xml UrlBase and turns off EnableSsl (TLS is handled by Tailscale).
set -euo pipefail

fix_app() {
	local app="$1"
	local base="$2"
	if ! docker ps --format '{{.Names}}' | grep -qx "${app}"; then
		echo "Skip ${app}: container not running"
		return
	fi
	echo "=== Fixing ${app} (UrlBase ${base}) ==="
	docker exec "${app}" sed -i \
		-e "s|<UrlBase>.*</UrlBase>|<UrlBase>${base}</UrlBase>|" \
		-e 's|<EnableSsl>.*</EnableSsl>|<EnableSsl>False</EnableSsl>|' \
		/config/config.xml
	docker restart "${app}" >/dev/null
	sleep 5
	docker exec "${app}" grep -E 'UrlBase|EnableSsl' /config/config.xml
}

fix_app sonarr /sonarr
fix_app radarr /radarr

echo ""
echo "Test (expect HTTP/1.1 401 or 200, NOT a chain of 302s):"
curl -sI http://127.0.0.1:8190/sonarr/ | head -5
curl -sI http://127.0.0.1:8190/radarr/ | head -5
