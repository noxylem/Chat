<!-- BEGIN:nextjs-agent-rules -->
# This is NOT the Next.js you know

This version has breaking changes — APIs, conventions, and file structure may all differ from your training data. Read the relevant guide in `node_modules/next/dist/docs/` before writing any code. Heed deprecation notices.
<!-- END:nextjs-agent-rules -->

## Cursor Cloud specific instructions

### Project Overview

Real-time chat application built with Next.js 16, Socket.IO, TypeScript, and Tailwind CSS 4.

### Services

| Service | Command | Port | Notes |
|---------|---------|------|-------|
| Dev server (Next.js + Socket.IO) | `pnpm dev` | 3000 | Custom server via `tsx server.ts`; serves both Next.js pages and Socket.IO WebSocket |

### Key Commands

All standard commands are in `package.json` scripts. Quick reference:

- **Dev**: `pnpm dev` — starts the custom Socket.IO + Next.js server on port 3000
- **Lint**: `pnpm lint` — runs ESLint with Next.js rules
- **Test**: `pnpm test` — runs Jest tests
- **Build**: `pnpm build` — production build via Next.js (does not use custom server)

### Non-obvious Notes

- The dev server uses `tsx server.ts` (not `next dev`) because it bundles a custom HTTP server with Socket.IO. The `pnpm dev:next` script runs vanilla Next.js without Socket.IO if needed.
- Jest tests use `@testing-library/jest-dom/jest-globals` import in each test file for custom matchers (not a global setup file), because Jest 30 runs `setupFiles` before the test framework initialises `expect`.
- The `pnpm.onlyBuiltDependencies` field in `package.json` whitelists `esbuild` for post-install scripts; this avoids the interactive `pnpm approve-builds` prompt.
