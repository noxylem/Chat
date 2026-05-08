"use client";

import { useState, useEffect, useRef, useCallback } from "react";
import { v4 as uuidv4 } from "uuid";
import { getSocket, ChatMessage } from "@/lib/socket";

interface ChatRoomProps {
  username: string;
  room: string;
}

export default function ChatRoom({ username, room }: ChatRoomProps) {
  const [messages, setMessages] = useState<ChatMessage[]>([]);
  const [inputText, setInputText] = useState("");
  const messagesEndRef = useRef<HTMLDivElement>(null);
  const socketRef = useRef(getSocket());

  const scrollToBottom = useCallback(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, []);

  useEffect(() => {
    const socket = socketRef.current;

    socket.emit("join-room", room);

    socket.on("message-history", (history: ChatMessage[]) => {
      setMessages(history);
    });

    socket.on("new-message", (message: ChatMessage) => {
      setMessages((prev) => [...prev, message]);
    });

    return () => {
      socket.off("message-history");
      socket.off("new-message");
      socket.emit("leave-room", room);
    };
  }, [room]);

  useEffect(() => {
    scrollToBottom();
  }, [messages, scrollToBottom]);

  const sendMessage = (e: React.FormEvent) => {
    e.preventDefault();
    if (!inputText.trim()) return;

    const message: ChatMessage = {
      id: uuidv4(),
      username,
      text: inputText.trim(),
      timestamp: Date.now(),
      room,
    };

    socketRef.current.emit("send-message", message);
    setInputText("");
  };

  const formatTime = (timestamp: number) => {
    return new Date(timestamp).toLocaleTimeString([], {
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  return (
    <div className="flex flex-col h-full">
      <div className="bg-white dark:bg-zinc-900 border-b border-zinc-200 dark:border-zinc-700 px-6 py-4">
        <h2 className="text-lg font-semibold text-zinc-900 dark:text-zinc-100">
          #{room}
        </h2>
        <p className="text-sm text-zinc-500 dark:text-zinc-400">
          Chatting as <span className="font-medium text-blue-600 dark:text-blue-400">{username}</span>
        </p>
      </div>

      <div className="flex-1 overflow-y-auto px-6 py-4 space-y-4 bg-zinc-50 dark:bg-zinc-950">
        {messages.length === 0 && (
          <div className="flex items-center justify-center h-full">
            <p className="text-zinc-400 dark:text-zinc-500 text-sm">
              No messages yet. Start the conversation!
            </p>
          </div>
        )}
        {messages.map((msg) => {
          const isOwn = msg.username === username;
          return (
            <div
              key={msg.id}
              className={`flex ${isOwn ? "justify-end" : "justify-start"}`}
            >
              <div
                className={`max-w-xs sm:max-w-md px-4 py-2 rounded-2xl ${
                  isOwn
                    ? "bg-blue-600 text-white rounded-br-md"
                    : "bg-white dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100 border border-zinc-200 dark:border-zinc-700 rounded-bl-md"
                }`}
              >
                {!isOwn && (
                  <p className="text-xs font-semibold text-blue-600 dark:text-blue-400 mb-1">
                    {msg.username}
                  </p>
                )}
                <p className="text-sm break-words">{msg.text}</p>
                <p
                  className={`text-xs mt-1 ${
                    isOwn ? "text-blue-200" : "text-zinc-400 dark:text-zinc-500"
                  }`}
                >
                  {formatTime(msg.timestamp)}
                </p>
              </div>
            </div>
          );
        })}
        <div ref={messagesEndRef} />
      </div>

      <form
        onSubmit={sendMessage}
        className="bg-white dark:bg-zinc-900 border-t border-zinc-200 dark:border-zinc-700 px-6 py-4"
      >
        <div className="flex gap-3">
          <input
            type="text"
            value={inputText}
            onChange={(e) => setInputText(e.target.value)}
            placeholder="Type a message..."
            className="flex-1 px-4 py-2 rounded-full border border-zinc-300 dark:border-zinc-600 bg-zinc-50 dark:bg-zinc-800 text-zinc-900 dark:text-zinc-100 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-sm"
            autoFocus
          />
          <button
            type="submit"
            disabled={!inputText.trim()}
            className="px-6 py-2 bg-blue-600 text-white rounded-full text-sm font-medium hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
          >
            Send
          </button>
        </div>
      </form>
    </div>
  );
}
