import React, { useState, useRef, useEffect } from "react";

export default function TerminalTextarea() {
  const [lines, setLines] = useState(["&gt>"]);
  const textareaRef = useRef(null);

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      const input = e.target.value.split("\n").pop(); // get last line
      const newLine = `&gt> ${input}`;

      // Example output: echo typed command
      setLines((prev) => [...prev, newLine, "&gt> "]);

      e.target.value = ""; // clear input
    }
  };

  useEffect(() => {
    const container = textareaRef.current.parentElement;
    if (container) container.scrollTop = container.scrollHeight; // auto scroll
  }, [lines]);

  return (
    <div
      style={{
        width: "100%",
        height: "250px",
        background: "#000",
        color: "#0f0",
        padding: "8px",
        fontFamily: "monospace",
        overflowY: "auto",
      }}
    >
      <div style={{ whiteSpace: "pre-wrap" }}>{lines.join("\n")}</div>
      <textarea
        ref={textareaRef}
        onKeyDown={handleKeyDown}
        rows={2}
      />
    </div>
  );
}
