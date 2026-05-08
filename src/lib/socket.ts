import { io, Socket } from "socket.io-client";

let socket: Socket | null = null;

export function getSocket(): Socket {
  if (!socket) {
    socket = io({
      path: "/api/socketio",
      addTrailingSlash: false,
    });
  }
  return socket;
}

export interface ChatMessage {
  id: string;
  username: string;
  text: string;
  timestamp: number;
  room: string;
}
