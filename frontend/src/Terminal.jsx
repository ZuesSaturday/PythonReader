// import React,{use, useEffect,useRef} from "react";
// import { Terminal } from 'xterm';
// import 'xterm/css/xterm.css';

// function TerminalComponent() {
//     const terminalRef = useRef(null);

//     useEffect(() => {
//         const term = new Terminal();
//         term.open(terminalRef.current);
//         term.writeln('Welcome to the Terminal!\r\n$ ');
        
//         term.onData(date => {
//             term.write(Data);
//         });

//         return () => term.dispose();
//     },[]);

//     return (
//         <div className="terminal-container" ref={terminalRef} style={{width: '100%', height: '100%'}}></div>
//     );
// }
// export default TerminalComponent;   