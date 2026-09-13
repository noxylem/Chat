#!/usr/bin/env bash
# Run ON jpc — fixes blank Nextcloud page behind /nextcloud subpath.
set -euo pipefail

NC="${1:-nextcloud}"
BASE_URL="https://jpc.wyrm-pike.ts.net:10000/nextcloud"
HOST="jpc.wyrm-pike.ts.net:10000"

if ! docker ps --format '{{.Names}}' | grep -qx "${NC}"; then
	echo "Container '${NC}' not running. Usage: $0 [container-name]"
	exit 1
fi

occ() {
	docker exec -u www-data "${NC}" php occ "$@"
}

echo "=== Nextcloud reverse-proxy settings ==="
occ config:system:set trusted_proxies 0 --value='127.0.0.1'
occ config:system:set trusted_proxies 1 --value='172.16.0.0/12'
occ config:system:set overwriteprotocol --value='https'
occ config:system:set overwritewebroot --value='/nextcloud'
occ config:system:set overwrite.cli.url --value="${BASE_URL}"
occ config:system:set overwritehost --value="${HOST}"
occ config:system:set htaccess.RewriteBase --value='/nextcloud'

# Ensure MagicDNS hostname is trusted
if ! occ config:system:get trusted_domains | grep -q 'jpc.wyrm-pike.ts.net'; then
	occ config:system:set trusted_domains 2 --value='jpc.wyrm-pike.ts.net'
fi

echo ""
echo "=== Current values ==="
occ config:system:get trusted_domains
occ config:system:get trusted_proxies
occ config:system:get overwriteprotocol
occ config:system:get overwritewebroot
occ config:system:get overwrite.cli.url
occ config:system:get overwritehost
occ config:system:get htaccess.RewriteBase

echo ""
echo "Open: ${BASE_URL}/"
echo "Use a private/incognito window after changing settings."
