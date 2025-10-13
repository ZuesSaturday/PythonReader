
const textArea = document.getElementById('code');
const lineNumbers = document.getElementById('line-numbers');
const input = document.getElementById("terminal-input")
const inputLine = document.getElementById("input-line");
const terminalBody = document.getElementById("terminal-body");
const terminalCaller = document.getElementById("terminal-caller");


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