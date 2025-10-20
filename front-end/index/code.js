const terminal = document.getElementById('terminal');

terminal.addEventListener('keydown', function(e) {
    if (e.target.classList.contains('input-line') && e.key === 'Enter') {
        e.preventDefault(); // prevent newline inside current textarea

        const currentInput = e.target;
        const value = currentInput.value;

        // Make current textarea read-only
        currentInput.setAttribute('readonly', 'true');

        // Optionally, append the value as text above the new input
        const output = document.createElement('div');
        output.textContent = "> " + value;
        terminal.insertBefore(output, currentInput.parentElement);

        // Create new input line
        const newInputDiv = document.createElement('div');
        newInputDiv.classList.add('terminal-input');
        newInputDiv.innerHTML = `<span>&gt;</span><textarea type="text" class="input-line"></textarea>`;
        terminal.appendChild(newInputDiv);

        // Focus on new textarea
        newInputDiv.querySelector('textarea').focus();
    }
});