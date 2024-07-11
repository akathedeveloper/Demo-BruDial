import React from 'react';
import { Link } from 'react-router-dom';
import "../css/LandingPage.css"
import image from './Login.png';

const LandingPage = () => {
  return (

    <div className="login-page">
      <div className="circle top-right"></div>
      <div className="circle bottom-right"></div>
      <div className="login-container">
        <div className="login-left">
          <div className="logo">
            <div className="square"></div>
            <h3>BruDial</h3>
          </div>
          <div className='welcome-text'>
            <h2>Welcome To BruDial</h2>
          </div>
          <div className='welcome-para'>
            <p>To keep connected with us please login with your personal information by email addresss and password</p>
          </div>
          <div className="button-container">
        <Link to="/user/login" className="landing-button">Login</Link>
      </div>
        </div>
        <div className="login-right">
          <img src={image} alt="Login Illustration" />
        </div>
      </div>
    </div>
  );
};

export default LandingPage;
