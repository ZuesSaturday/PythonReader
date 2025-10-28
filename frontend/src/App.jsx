// import { useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
// import './App.css'
//
// function App() {
//   const [count, setCount] = useState(0)
//
//   return (
//     <>
//       <div>
//         <a href="https://vite.dev" target="_blank">
//           <img src={viteLogo} className="logo" alt="Vite logo" />
//         </a>
//         <a href="https://react.dev" target="_blank">
//           <img src={reactLogo} className="logo react" alt="React logo" />
//         </a>
//       </div>
//       <h1>Vite + React</h1>
//       <div className="card">
//         <button onClick={() => setCount((count) => count + 1)}>
//           count is {count}
//         </button>
//         <p>
//           Edit <code>src/App.jsx</code> and save to test HMR
//         </p>
//       </div>
//       <p className="read-the-docs">
//         Click on the Vite and React logos to learn more
//       </p>
//     </>
//   )
// }
//
// export default App

import React, { useState } from "react";

export default function App {
    const [code, setCode] = useState("");
    const [output, setOutput] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState("");

    const handleRun = async () => {
        setLoading(true);
        setOutput("");
        setError("");

        try {
            const response = await fetch("https://localhost:8080/run",{
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(code),
                });

                if (!response.ok) throw new Error("Service error");
                const result = await response.json();

                setOutput(result.output || "");
                setError(result.error || "");

            }catch (err) {
                setError("Failde to connect to backend");
            }finally {
                setLoading(false);
            }
        };

        return (
            <div className="flex flex-col items-center min-h-screen bg-[#111] text-[#eee] p-6">
              <h1 className="text-3xl font-bold mb-6 text-[#7fffd4]">
                PythonReader Interpreter
              </h1>

              <textarea
                className="w-full max-w-2xl h-48 p-4 text-[#0f0] bg-[#222] border border-[#333] rounded-lg font-mono text-sm resize-none focus:outline-none focus:border-[#7fffd4]"
                placeholder="Type your Saturday code here..."
                value={code}
                onChange={(e) => setCode(e.target.value)}
              />

              <button
                onClick={handleRun}
                disabled={loading}
                className="mt-4 px-6 py-2 bg-[#7fffd4] text-black font-semibold rounded-lg hover:bg-[#5be2b0] disabled:opacity-50"
              >
                {loading ? "Running..." : "Run"}
              </button>

                  <div className="w-full max-w-2xl mt-6 bg-[#1a1a1a] rounded-lg p-4 font-mono text-sm border border-[#333]">
                        <p className="text-[#7fffd4] mb-2 font-semibold">Output:</p>
                        <pre className="text-[#0f0] whitespace-pre-wrap">{output}</pre>

                        {error && (
                              <>
                                <p className="text-red-400 mt-4 font-semibold">Error:</p>
                                <pre className="text-red-400 whitespace-pre-wrap">{error}</pre>
                              </>
                        )}
                  </div>
            </div>
        );
    }