import React from 'react';
import logo from './assets/DAROARA_icon.svg'


function Header({ onRun , toggleTerminal}) {

    return(
        <header>
            <div className="header-container">
                <div className="logo">
                    <div className="logo-icon"><img src={logo} alt="" ></img></div>
                </div>
                <div className="header-menu">
                    <nav>
                        <ul>
                            <li><button >File</button></li>
                            <li><button >Edit</button></li>
                            <li><button >Selection</button></li>
                            <li><button >View</button></li>
                            <li><button onClick={onRun}>Run</button></li>
                            <li><button onClick={toggleTerminal}>Terminal</button>
                            </li>
                            <li><button >Help</button></li>
                        </ul>
                    </nav>
                </div>
                <div className="header-search">
                    <input type="text" placeholder=" Search..." className="icon-input"/>
                </div>
            </div>
        </header>
    );
}
export default Header