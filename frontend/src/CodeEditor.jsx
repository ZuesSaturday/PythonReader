import React, { useEffect, useRef } from "react";
import { EditorState } from "@codemirror/state";
import { EditorView, keymap } from "@codemirror/view";
import { defaultKeymap } from "@codemirror/commands";
import { python } from "@codemirror/lang-python";
import { oneDark } from "@codemirror/theme-one-dark";
import { lineNumbers } from "@codemirror/view";

function CodeEditor({ initialCode, onChange }) {
  const editorRef = useRef(null);
  const viewRef = useRef(null);

  useEffect(() => {
    if (!editorRef.current) return;

    const state = EditorState.create({
      doc: initialCode,
      extensions: [
        keymap.of(defaultKeymap),
        python(),
        oneDark,
        lineNumbers(),
        EditorView.updateListener.of((update) => {
          if (update.docChanged) {
            const code = update.state.doc.toString();
            onChange?.(code);
          }
        })
      ]
    });

    viewRef.current = new EditorView({
      state,
      parent: editorRef.current
    });

    return () => viewRef.current.destroy();
  }, [initialCode, onChange]);

  return <div ref={editorRef} id ="editor" className="code-editor"></div>;
}

export default CodeEditor;
