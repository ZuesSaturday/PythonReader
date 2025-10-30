import React, { useEffect, useRef } from "react";
import { Terminal } from "xterm";
import { io } from "socket.io-client";
import "xterm/css/xterm.css";

export default function TerminalComponent() {
  const terminalRef = useRef(null);
  const socketRef = useRef(null);

  useEffect(() => {
    // Create terminal instance
    const term = new Terminal({
      rows: 25,
      cols: 80,
      cursorBlink: true,
      theme: { background: "#000000" },
    });
    term.open(terminalRef.current);

    // Connect to backend socket
    socketRef.current = io("http://localhost:3001"); 
    // backend URL

    // When backend sends data, write to terminal
    socketRef.current.on("output", (data) => {
      term.write(data);
    });

    // When user types, send data to backend
    term.onData((data) => {
      socketRef.current.emit("input", data);
    });

    return () => {
      term.dispose();
      socketRef.current.disconnect();
    };
  }, []);

  return (
    <div
      ref={terminalRef}
        style={{
        width: "100%",
        height: "400px",
        backgroundColor: "black",
      }}
    />
  );
}
