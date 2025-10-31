import React from 'react';
function Sidebar() {
    return(
        <aside className="sidebar">
            <div className="sidebar-top-section">
                <ul>
                    <li><a href="#">Ex</a></li>
                    <li><a href="#">Se</a></li>
                    <li><a href="#">So</a></li>
                    <li><a href="#">Ex</a></li>
                </ul>
            </div>
            <div className="sidebar-bottom-section">
                <ul>
                    <li><a href="#"> Acc</a></li>
                    <li><a href="#"> Set</a></li>
                </ul>
            </div>
        </aside>
    );
}
export default Sidebar