import React from 'react';
import '../css/Navbar.css';
import img from './phone.png'

const Navbar = () => {
  return (
    <div className="navbar">
      <div className="navbar-logo">
        <img src={img} alt="Logo" />
        <span className="navbar-name">BruDial</span>
      </div>
      <div className="navbar-login">
        <button>Login</button>
      </div>
    </div>
  );
};

export default Navbar;
