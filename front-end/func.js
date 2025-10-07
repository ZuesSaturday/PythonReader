    //   <script>
    const textArea = document.getElementById('code');
    const lineNumbers = document.getElementById('line-numbers');

    // Function to update line numbers
    function updateLineNumbers() {
      const lines = textArea.value.split('\n').length;
      lineNumbers.innerHTML = '';
      for (let i = 1; i <= lines; i++) {
        const lineNumber = document.createElement('span');
        lineNumber.textContent = i;
        lineNumbers.appendChild(lineNumber);
      }
    }

    // Function to sync scrolling between textarea and line numbers
    function syncScroll() {
      lineNumbers.scrollTop = textArea.scrollTop;
    }

    // Initialize line numbers
    updateLineNumbers();
  {/* </script> */}