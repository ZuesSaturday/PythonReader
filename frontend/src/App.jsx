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
          <div className='main-editor-terminal'>
            <Main/>
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
