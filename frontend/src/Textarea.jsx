import React, { useRef, useEffect } from "react";

function Main({textAreaRef}) {
  const lineNumbersRef = useRef(null);

  const updateLineNumbers = () => {
    const textArea = textAreaRef.current;
    const lineNumbers = lineNumbersRef.current;
    const lines = textArea.value.split("\n").length;

    lineNumbers.innerHTML = "";
    for (let i = 1; i <= lines; i++) {
      const lineNumber = document.createElement("div");
      lineNumber.textContent = i;
      lineNumbers.appendChild(lineNumber);
    }
  };

  const syncScroll = () => {
    lineNumbersRef.current.scrollTop = textAreaRef.current.scrollTop;
  };

  useEffect(() => {
    updateLineNumbers();
  }, []);

  return (
    <main>
      <div className="editor" id="editor">
        <div className="line-numbers" ref={lineNumbersRef}></div>
        <textarea
          ref={textAreaRef}
          id="code"
          onInput={updateLineNumbers}
          onScroll={syncScroll}
        ></textarea>
      </div>
    </main>
  );
}

export default Main;
