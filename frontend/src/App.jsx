import { useRef, useState } from 'react';
import Header from './header.jsx'
import Sidebar from './Sidebar.jsx'
import Footer from './footer.jsx'
import Main from './Textarea.jsx'
import TerminalComponent from './Terminal.jsx';


function App() {
    const [showTerminal, setTerminal] = useState(false);
    const textAreaRef = useRef(null);

    const toggleTerminal = () => {
      setTerminal((prev) => !prev);
    };

    ;
    const sendCode = async () => {
      const code = document.getElementById("code").value || "";

      try {
        const response = await fetch("http://localhost:8080/api/run", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ code }),
        });

        const result = await response.json();
        alert(result.output || result.error);
      }catch (error) {
        console.error("Error sending code to backend:", error);
      }
    };

    return(
      <>
        <Header onRun={sendCode} toggleTerminal={toggleTerminal} />
        <Sidebar/>
          <div className='main-editor-terminal'>
            <Main textAreaRef={textAreaRef} />
            {showTerminal && (
              <div id="terminal" className='terminal-container'>
                <TerminalComponent/>
              </div>
            )}
          </div>

        <Footer/>
      </>

    );
}

export default App
