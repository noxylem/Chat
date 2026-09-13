#!/usr/bin/env bash
# Run ON james-laptop (with Tailscale connected) to debug :10000 access.
set -euo pipefail

TARGET="jpc.wyrm-pike.ts.net"
URL="https://${TARGET}:10000"

echo "=== Tailscale status ==="
tailscale status | head -20 || true

echo ""
echo "=== Exit node (must be empty for home server access) ==="
tailscale status --json 2>/dev/null | grep -i exit || echo "(check Tailscale menu: Exit Node should be 'None')"

echo ""
echo "=== DNS ==="
getent hosts "${TARGET}" || true

echo ""
echo "=== HTTPS tests from THIS machine ==="
for path in / /sonarr/ /nextcloud/ /tandoor/ /portainer/; do
	printf "%-20s " "${path}"
	curl -skI --max-time 10 "${URL}${path}" | head -1 || echo "FAILED"
done

echo ""
echo "If you see 'Connection refused' or timeout → ACL or Tailscale Serve issue on jpc."
echo "If curl works here but browser fails → try private/incognito window."
echo "If exit node is ON → turn it OFF in Tailscale on this laptop."
