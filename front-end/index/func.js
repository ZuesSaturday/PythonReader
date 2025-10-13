
const textArea = document.getElementById('code');
const lineNumbers = document.getElementById('line-numbers');

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


const inputLine = document.getElementById("input-line");
const terminalBody = document.getElementById("terminal-body");
const terminalCaller = document.getElementById("terminal-caller");

terminalCaller.addEventListener("click", function(event) {
    event.preventDefault(); // ✅ prevents page reload if inside a <form>

    const text = inputLine.value.trim();
    if (text === "") return; // ✅ ignore empty input

    const line = document.createElement("div");
    line.textContent = "> " + text;
    terminalBody.appendChild(line);

    terminalBody.scrollTop = terminalBody.scrollHeight;
    inputLine.value = "";
});




