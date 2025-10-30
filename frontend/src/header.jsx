import logo from './assets/DAROARA_icon.svg'

function Header() {
    return(
        <header>
            <div className="header-container">
                <div className="logo">
                    <div className="logo-icon"><img src={logo} alt="" ></img></div>
                </div>
                <div className="header-menu">
                    <nav>
                        <ul>
                            <li><a href="#">File</a></li>
                            <li><a href="#">Edit</a></li>
                            <li><a href="#">Selection</a></li>
                            <li><a href="#">View</a></li>
                            <li><a href="#" id="Runbutton">Run</a></li>
                            <li><a href="#" id="terminal-caller">Terminal</a></li>
                            <li><a href="#">Help</a></li>
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