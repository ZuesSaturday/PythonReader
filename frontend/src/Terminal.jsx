import React, { useState, useRef, useEffect } from "react";

export default function TerminalTextarea() {
  const [lines, setLines] = useState(["&gt>"]);
  const textareaRef = useRef(null);

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      const input = e.target.value.split("\n").pop();
      setLines((prev) => [...prev, "&gt> ~${input}","&gt>"]);
      e.target.value = ""; 
    }
  };

  useEffect(() => {
    const container = textareaRef.current.parentElement;
    if (container) container.scrollTop = container.scrollHeight;
  }, [lines]);

  return (
    <div
      style={{ background: "#000", color: "#0f0", fontFamily: "monospace", padding: "8px", height: "200px", overflowY: "auto" }}>
      <div style={{ whiteSpace: "pre-wrap" }}>{lines.join("\n")}</div>
      <textarea ref={textareaRef} onKeyDown={handleKeyDown} rows={2}/>
    </div>
  );
}
