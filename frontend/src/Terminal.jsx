import React, { useState, useRef, useEffect } from "react";

export default function Terminal() {
  const [lines, setLines] = useState([{ text: "@Zues>", color: "#0ff" }]); // cyan prompt
  const [input, setInput] = useState("");
  const [history, setHistory] = useState([]);
  const [historyIndex, setHistoryIndex] = useState(-1);
  const bottomRef = useRef(null);

  // Auto-scroll to bottom
  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [lines]);

  // Send command to backend
  const sendToBackend = async (command) => {
    // Show loading indicator
    setLines((prev) => [...prev, { text: "running...", color: "#888" }]);

    try {
      const response = await fetch("http://localhost:8080/api/run", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ code: command }),
      });

      const result = await response.json();

      // Remove loading indicator
      setLines((prev) => prev.slice(0, -1));

      if (result.output) {
        setLines((prev) => [...prev, { text: result.output, color: "#0f0" }]); // green
      }

      if (result.error) {
        setLines((prev) => [
          ...prev,
          { text: `ERROR: ${result.error}`, color: "#ff4444" }, // red
        ]);
      }
    } catch {
      setLines((prev) => [
        ...prev,
        { text: "ERR: Backend unreachable", color: "#ff4444" },
      ]);
    }

    // New prompt
    setLines((prev) => [...prev, { text: "@Zues>", color: "#0ff" }]);
  };

  // Handle input + history navigation
  const handleKeyDown = async (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      const command = input.trim();

      // Show typed command in terminal
      setLines((prev) => [
        ...prev.slice(0, -1), // remove current prompt
        { text: "@Zues> " + command ,color: "#0ff" },
      ]);

      // Add to history
      if (command) {
        setHistory((prev) => [...prev, command]);
        setHistoryIndex(-1);
      }

      setInput("");

      await sendToBackend(command);
    }

    // Navigate history with Up arrow
    if (e.key === "ArrowUp") {
      if (history.length === 0) return;

      const newIndex =
        historyIndex === -1 ? history.length - 1 : Math.max(0, historyIndex - 1);

      setHistoryIndex(newIndex);
      setInput(history[newIndex]);
    }

    // Navigate history with Down arrow
    if (e.key === "ArrowDown") {
      if (history.length === 0) return;

      const newIndex =
        historyIndex === -1
          ? -1
          : Math.min(history.length - 1, historyIndex + 1);

      setHistoryIndex(newIndex);

      if (newIndex === -1) setInput("");
      else setInput(history[newIndex]);
    }
  };

  return (
    <div
      style={{
        background: "#000",
        color: "#0f0",
        fontFamily: "monospace",
        padding: "10px",
        height: "400px",
        overflowY: "auto",
        borderRadius: "8px",
      }}
    >
      {lines.map((line, i) => (
        <div key={i} style={{ color: line.color }}>
          {line.text}
        </div>
      ))}

      {/* Visible input line */}
      <div style={{ display: "flex", alignItems: "center", marginTop: "4px" }}>
        <span style={{ color: "#0ff" }}>{""}</span>
        <input
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={handleKeyDown}
          autoFocus
          style={{
            background: "#000",
            color: "#fff",
            border: "none",
            outline: "none",
            marginLeft: "6px",
            width: "100%",
            fontFamily: "monospace",
            fontSize: "14px",
          }}
        />
      </div>

      <div ref={bottomRef} />
    </div>
  );
}
