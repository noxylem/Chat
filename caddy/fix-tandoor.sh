#!/usr/bin/env bash
# Run ON jpc — documents Tandoor subpath env vars and restarts the container.
# Edit ~/docker/tandoor/docker-compose.yml (or wherever tandoor lives) then re-run.
set -euo pipefail

TANDOOR_DIR="${HOME}/docker/tandoor"
COMPOSE="${TANDOOR_DIR}/docker-compose.yml"

cat <<'EOF'
=== Tandoor needs these environment variables in docker-compose.yml ===

  environment:
    - SCRIPT_NAME=/tandoor
    - STATIC_URL=/tandoor/static/
    - MEDIA_URL=/tandoor/media/
    - JS_REVERSE_SCRIPT_PREFIX=/tandoor/
    - REVERSE_PROXY_COUNT=2

(REVERSE_PROXY_COUNT=2 = Tandoor's built-in nginx + Caddy in front)

Then restart:
  cd ~/docker/tandoor && docker compose up -d

URL: https://jpc.wyrm-pike.ts.net:10000/tandoor/
EOF

if [[ -f "${COMPOSE}" ]]; then
	echo ""
	echo "=== Found ${COMPOSE} ==="
	if grep -q 'SCRIPT_NAME' "${COMPOSE}"; then
		echo "SCRIPT_NAME already present."
	else
		echo "WARNING: SCRIPT_NAME not found — add the env vars above under the tandoor service."
	fi
fi

if docker ps --format '{{.Names}}' | grep -qx 'tandoor'; then
	echo ""
	echo "=== Restarting tandoor ==="
	(cd "${TANDOOR_DIR}" 2>/dev/null && docker compose restart) || docker restart tandoor
	sleep 5
	echo -n "HTTP test: "
	curl -sI http://127.0.0.1:8190/tandoor/ | head -1 || true
fi
