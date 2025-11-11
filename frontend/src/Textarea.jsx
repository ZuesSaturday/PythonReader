import React, { useRef, useEffect } from "react";
import { EditorView, basicSetup } from "codemirror";
import { python } from "@codemirror/lang-python";
import { oneDark } from "@codemirror/theme-one-dark";

function Main({ textAreaRef }) {
  const editorContainer = useRef(null);

  useEffect(() => {
    if (!editorContainer.current) return;

    const view = new EditorView({
      doc: "",
      extensions: [
        basicSetup,
        python(),
        oneDark
      ],
      parent: editorContainer.current
    });

    // Store CodeMirror instance externally if needed
    textAreaRef.current = view;

    return () => view.destroy();
  }, []);

  return (
    <main>
      <div className="editor" id="editor">
        <div ref={editorContainer} className="cm-code-area"></div>
      </div>
    </main>
  );
}

export default Main;
