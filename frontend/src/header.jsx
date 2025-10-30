import logo from './assets/DAROARA_icon.svg'


function Header({onToggleTerminal}) {

    return(
        <header>
            <div className="header-container">
                <div className="logo">
                    <div className="logo-icon"><img src={logo} alt="" ></img></div>
                </div>
                <div className="header-menu">
                    <nav>
                        <ul>
                            <li><button href="#">File</button></li>
                            <li><button href="#">Edit</button></li>
                            <li><button href="#">Selection</button></li>
                            <li><button href="#">View</button></li>
                            <li><button href="#" id="Runbutton">Run</button></li>
                            <li>
                                <button 
                                    onClick={onToggleTerminal}
                                    >
                                    Terminal
                                </button>
                            </li>
                            <li><button href="#">Help</button></li>
                        </ul>
                    </nav>
                </div>
                <div className="header-search">
                    <input 
                    type="text" 
                    placeholder=" Search..." 
                    className="icon-input"
                    />
                </div>
            </div>
        </header>
    );
}
export default Header