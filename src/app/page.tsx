"use client";

import { useState } from "react";
import UsernameForm from "@/components/UsernameForm";
import ChatRoom from "@/components/ChatRoom";

export default function Home() {
  const [user, setUser] = useState<{ username: string; room: string } | null>(
    null
  );

  if (!user) {
    return (
      <UsernameForm
        onSubmit={(username, room) => setUser({ username, room })}
      />
    );
  }

  return (
    <div className="h-screen flex flex-col">
      <ChatRoom username={user.username} room={user.room} />
    </div>
  );
}
