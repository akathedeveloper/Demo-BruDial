import React, {useState} from 'react';

import '../css/TopBar.css';
import {Link, useNavigate} from "react-router-dom";

const TopBar = ({ userDetails }) => {

    const [dropdownVisible, setDropdownVisible] = useState(false);
    const navigate = useNavigate();

    const toggleDropdown = () => {
        setDropdownVisible(!dropdownVisible);
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/user/login');
    };
    if (!userDetails) {
        return <div className="top-bar">Loading...</div>;
    }

    return (
        <div className="top-bar">

            <div className="user-detail">Profile Picture: {userDetails.profilePicture}</div>
            <div className="user-detail">Email: {userDetails.username || userDetails.email}</div>
            <div className="user-detail">Phone: {userDetails.assignedNumber || 'N/A'}</div>
            <div className="user-detail">First Name: {userDetails.firstName}</div>
            <div className="user-detail">Second Name: {userDetails.lastName}</div>
            <div className="hamburger-menu" onClick={toggleDropdown}>
                <div className="line"></div>
                <div className="line"></div>
                <div className="line"></div>
            </div>
            {dropdownVisible && (
                <div className="dropdown-menu">
                    <Link to="/profile" className='dropdown-item'>Update Profile</Link>
                    <button onClick={handleLogout} className='dropdown-item'>Logout</button>
                </div>
            )}
        </div>
    );
};

export default TopBar;
