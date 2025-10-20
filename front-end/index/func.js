
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
const handler = document.querySelector('handle');
handler.addEventListener("keydown", (e) => {
  if (e.target.classList.contains('line') && e.key === 'Enter') {
        e.preventDefault();

        const currentInput = e.target;
        const value = currentInput.value;

        // Make current textarea read-only
        currentInput.setAttribute('readonly', 'true');

        // Optionally, append the value as text above the new input
        // const output = document.createElement('div');
        // output.textContent = "> " + value;
        // terminal.insertBefore(output, currentInput.parentElement);

        // Create new input line
        const newInputDiv = document.createElement('div');
        newInputDiv.classList.add('terminal-input');
        newInputDiv.innerHTML = `<span>&gt;</span><textarea type="text" id="input-line"></textarea>`;
        handler.appendChild(newInputDiv);

        // Focus on new textarea
        newInputDiv.querySelector('textarea').focus();
  }
})

// handle.addEventListener('mousedown', (e) =>{
//   isResizing = true;
//   startY = e.cliemtY;
//   startHeight = parseInt(document.defaultView.getComputedStyle(terminal).height, 10);
//   document.body.style.userSelect = 'none';
// });

// document.addEventListener('mousemove', (e) =>{
//   if (!isResizing) return;
//   const newHeight = startHeight +(startY - e.clientY);
//   terminal.style.height = newHeight + 'px';
// });

// document.addEventListener('mouseup', () =>{
//   isResizing = false;
//   document.body.style.userSelect = 'auto';
// });