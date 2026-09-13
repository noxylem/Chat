#!/usr/bin/env bash
# Run this ON jpc (Pop!_OS) at the machine itself.
# Sets up Caddy + Tailscale Serve for HTTPS routing.
set -euo pipefail

CADDY_DIR="${HOME}/docker/caddy"
ACL_FILE="${HOME}/docker/tailscale/acl.json"

echo "=== 1. Check Docker containers are running ==="
docker ps --format "table {{.Names}}\t{{.Ports}}" | grep -iE 'vault|nextcloud|tandoor|portainer|sonarr|radarr|caddy' || true

echo ""
echo "=== 2. Start Caddy ==="
mkdir -p "${CADDY_DIR}"
cd "${CADDY_DIR}"
docker compose up -d
sleep 2

echo ""
echo "=== 3. Sonarr / Radarr config (must match Caddyfile) ==="
for app in sonarr radarr; do
	if docker ps --format '{{.Names}}' | grep -qx "${app}"; then
		echo "--- ${app} ---"
		docker exec "${app}" grep -E 'UrlBase|EnableSsl' /config/config.xml || true
	fi
done

echo ""
echo "=== 4. Test Caddy locally (HTTP) ==="
echo -n "Vaultwarden listener (:8180): "
curl -s -o /dev/null -w "%{http_code}" http://127.0.0.1:8180 || echo "FAIL"
echo ""
echo -n "Sonarr via Caddy (:8190/sonarr/): "
curl -sI http://127.0.0.1:8190/sonarr/ | head -1 || echo "FAIL"
echo ""
echo -n "Portainer (:8190/portainer/): "
curl -s -o /dev/null -w "%{http_code}" http://127.0.0.1:8190/portainer/ || echo "FAIL"
echo ""

echo ""
echo "=== 5. Configure Tailscale Serve ==="
sudo tailscale serve reset
sudo tailscale serve --bg --https=443    http://127.0.0.1:8180
sudo tailscale serve --bg --https=10000  http://127.0.0.1:8190
tailscale serve status

echo ""
echo "=== 6. Test HTTPS via Tailscale ==="
echo -n "Vaultwarden: "
curl -sk -o /dev/null -w "%{http_code}" https://jpc.wyrm-pike.ts.net || echo "FAIL"
echo ""
echo -n "Sonarr admin: "
curl -skI https://jpc.wyrm-pike.ts.net:10000/sonarr/ | head -1 || echo "FAIL"
echo ""
echo -n "Portainer admin: "
curl -sk -o /dev/null -w "%{http_code}" https://jpc.wyrm-pike.ts.net:10000/portainer/ || echo "FAIL"
echo ""

echo ""
echo "=== Done ==="
echo "Vaultwarden:  https://jpc.wyrm-pike.ts.net"
echo "Sonarr:         https://jpc.wyrm-pike.ts.net:10000/sonarr/"
echo "Admin apps:     https://jpc.wyrm-pike.ts.net:10000/portainer/"
echo ""
if [[ -f "${ACL_FILE}" ]]; then
	echo "ACL file saved at: ${ACL_FILE}"
	echo "Paste it into https://login.tailscale.com/admin/acls"
else
	echo "Save acl.json to ${ACL_FILE} and paste into Tailscale admin."
fi
