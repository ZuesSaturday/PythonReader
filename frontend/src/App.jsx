import React from 'react';
import Header from './header.jsx'
import Sidebar from './Sidebar.jsx'
import Footer from './footer.jsx'
import Main from './Textarea.jsx'
import TerminalComponent from './Terminal.jsx';

function App() {
    const [showTerminal, setTerminal] = React.useState(false);

    const ToggleTerminal = () => {
      setTerminal((prev) => !prev);
    };
    return(
      <>
        <Header onToggleTerminal={ToggleTerminal} />
        <Sidebar/>
        <Main/>
        {showTerminal && (
          <div 
            id="terminal"
            style={{
            display: "flex",
            height: "300px",
            borderTop: "1px solid #444",
          }}
          >
            <TerminalComponent/>
          </div>
        )}
        <Footer/>
      </>

    );
}

export default App
