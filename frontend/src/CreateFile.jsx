import { useState } from "react";

export default function CreateFile() {
  const [folder, setFolder] = useState("");
  const [file, setFile] = useState("");
  const [content, setContent] = useState("");
  const [response, setResponse] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const res = await fetch("http://localhost:8080/api/files/create", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        folderName: folder,
        fileName: file,
        content: content,
      }),
    });

    const text = await res.text();
    setResponse(text);
  };

  return (
    <div>
      <h2>Create Folder + File</h2>

      <form onSubmit={handleSubmit}>
        <input
          placeholder="Folder name"
          value={folder}
          onChange={(e) => setFolder(e.target.value)}
        /><br/>

        <input
          placeholder="File name (example: test.txt)"
          value={file}
          onChange={(e) => setFile(e.target.value)}
        /><br/>

        <textarea
          placeholder="File content"
          value={content}
          onChange={(e) => setContent(e.target.value)}
        ></textarea><br/>

        <button type="submit">Create</button>
      </form>

      <p>{response}</p>
    </div>
  );
}
