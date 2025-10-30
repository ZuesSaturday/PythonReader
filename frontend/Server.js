import express from "express";
import http from "http";
import { Server } from "socket.io";
import { spawn } from "child_process";
import cors from "cors";

const app = express();
app.use(cors());
const server = http.createServer(app);
const io = new Server(server, {
  cors: { origin: "http://localhost:5173" } // or your React dev port
});

io.on("connection", (socket) => {
  console.log("✅ Terminal connected");

  // Spawn a shell (bash on Linux/macOS, powershell on Windows)
  const shell = spawn(process.platform === "win32" ? "powershell.exe" : "bash", [], {
    stdio: "pipe"
  });

  // Send shell output to frontend
  shell.stdout.on("data", (data) => {
    socket.emit("output", data.toString());
  });

  shell.stderr.on("data", (data) => {
    socket.emit("output", data.toString());
  });

  // When frontend sends input
  socket.on("input", (data) => {
    shell.stdin.write(data);
  });

  socket.on("disconnect", () => {
    shell.kill();
    console.log("❌ Terminal disconnected");
  });
});

server.listen(3001, () => console.log("Server running on port 3001"));
