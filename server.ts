import { createServer } from "http";
import next from "next";
import { Server as SocketIOServer } from "socket.io";

const dev = process.env.NODE_ENV !== "production";
const hostname = "localhost";
const port = parseInt(process.env.PORT || "3000", 10);

const app = next({ dev, hostname, port });
const handler = app.getRequestHandler();

interface ChatMessage {
  id: string;
  username: string;
  text: string;
  timestamp: number;
  room: string;
}

const messageHistory: Map<string, ChatMessage[]> = new Map();
const MAX_HISTORY = 100;

app.prepare().then(() => {
  const httpServer = createServer(handler);

  const io = new SocketIOServer(httpServer, {
    path: "/api/socketio",
    addTrailingSlash: false,
  });

  io.on("connection", (socket) => {
    console.log(`Client connected: ${socket.id}`);

    socket.on("join-room", (room: string) => {
      socket.join(room);
      const history = messageHistory.get(room) || [];
      socket.emit("message-history", history);
      console.log(`${socket.id} joined room: ${room}`);
    });

    socket.on("leave-room", (room: string) => {
      socket.leave(room);
      console.log(`${socket.id} left room: ${room}`);
    });

    socket.on("send-message", (message: ChatMessage) => {
      const room = message.room;
      if (!messageHistory.has(room)) {
        messageHistory.set(room, []);
      }
      const history = messageHistory.get(room)!;
      history.push(message);
      if (history.length > MAX_HISTORY) {
        history.shift();
      }
      io.to(room).emit("new-message", message);
    });

    socket.on("disconnect", () => {
      console.log(`Client disconnected: ${socket.id}`);
    });
  });

  httpServer.listen(port, () => {
    console.log(`> Ready on http://${hostname}:${port}`);
  });
});
