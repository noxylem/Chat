import { ChatMessage } from "@/lib/socket";

describe("ChatMessage interface", () => {
  it("creates a valid chat message object", () => {
    const message: ChatMessage = {
      id: "test-id-123",
      username: "Alice",
      text: "Hello, World!",
      timestamp: Date.now(),
      room: "general",
    };

    expect(message.id).toBe("test-id-123");
    expect(message.username).toBe("Alice");
    expect(message.text).toBe("Hello, World!");
    expect(message.room).toBe("general");
    expect(typeof message.timestamp).toBe("number");
  });
});
