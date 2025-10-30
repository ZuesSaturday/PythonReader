function Footer() {
    return(
        <footer>
            <div className="footer-content">
                <div className="footer-section"></div>
                <div className="footer-section"></div>
                <div className="footer-section"></div>
                <p>&copy; {new Date().getFullYear()} DAROARA. All rights reserved.</p>
            </div>
            {/* <div className="footer-bottom"></div> */}
        </footer>
    );
}

export default Footer