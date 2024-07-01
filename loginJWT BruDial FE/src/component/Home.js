import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../css/Home.css';
<<<<<<< HEAD
import TopBar from './TopBar'
=======

>>>>>>> 3f232066c25d45d03a615924c85576d2853129d0
function Home({ token }) {
  const [userDetails, setUserDetails] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUserDetails = async () => {
      try {
        const response = await axios.get('http://localhost:8080/home', {
          headers: { Authorization: `Bearer ${token}` }
        });
        setUserDetails(response.data.data);
      } catch (error) {
        console.error('Failed to fetch user details', error);
      }
    };

    fetchUserDetails();
  }, [token]);

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  if (!userDetails) return <div>Loading...</div>;

  return (
<<<<<<< HEAD
    <>
    
      <TopBar userDetails={userDetails}/>
=======
>>>>>>> 3f232066c25d45d03a615924c85576d2853129d0
    <div className="home-container">
      <h1>Welcome, {userDetails.firstName} {userDetails.lastName}</h1>
      <p>Email: {userDetails.email}</p>
      <p>Role: {userDetails.role}</p>
      <p>Gender: {userDetails.gender}</p>
      <p>Country: {userDetails.country}</p>

      {userDetails.role === 'ADMIN' && (
        <button className="register-toggle" onClick={() => navigate('/register')}>
          Register New User
        </button>
      )}

      <button className="chats-button" onClick={() => navigate('/home/chat')}>
        Chats
      </button>

      <button className="logout-button" onClick={handleLogout}>Logout</button>
    </div>
<<<<<<< HEAD
    </>
=======
>>>>>>> 3f232066c25d45d03a615924c85576d2853129d0
  );
}

export default Home;
