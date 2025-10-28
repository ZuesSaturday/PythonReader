
const textArea = document.getElementById('code');
const lineNumbers = document.getElementById('line-numbers');
const input = document.getElementById("terminal-input")
const inputLine = document.getElementById("input-line");
const terminalBody = document.getElementById("terminal-body");
const terminalCaller = document.getElementById("terminal-caller");
const terminal = document.getElementById("terminal");
const handle = document.querySelector('.resize-handle');
const terminalInputLine = document.getElementById("terminal-input-line");

let isResizing = false;
let startY, startHeight;

function updateLineNumbers() {
  const lines = textArea.value.split('\n').length;
  lineNumbers.innerHTML = '';
  for (let i = 1; i <= lines; i++) {
    const lineNumber = document.createElement('span');
    lineNumber.textContent = i;
    lineNumbers.appendChild(lineNumber);
  }
}


function syncScroll() {
  lineNumbers.scrollTop = textArea.scrollTop;
}
updateLineNumbers();

function showPage() {
  const term = document.getElementById('terminal');
  term.style.display = term.style.display === 'none' ? 'flex' : 'none';

}

const Runbutton = document.getElementById("Runbutton");
function runCode() {
    const code = textArea.value;
    input.value = code;
    showPage()
    console.log(code);
}

const handler = document.querySelector(".terminal-input");
input.addEventListener("keypress", (e) => {
  if (e.key === "Enter") {
    e.preventDefault();

    e.target.disabled = true;

    const newInput = handler.cloneNode(true);

    const newTextarea = newInput.querySelector("textarea");
    newTextarea.value = "";
    newTextarea.disabled = false;

    terminal.appendChild(newInput);
    newInput.focus();
  }
});