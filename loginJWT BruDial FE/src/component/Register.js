import React, { useState } from 'react';
import axios from 'axios';
import '../css/Register.css';

function Register({ token, onSuccess }) {
  const [newUser, setNewUser] = useState({
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    gender: '',
    country: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewUser({ ...newUser, [name]: value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/register', newUser, {
        headers: { Authorization: `Bearer ${token}` }
      });
      alert('New user registered successfully');
      if (onSuccess) onSuccess();
    } catch (error) {
      console.error('Failed to register new user', error);
    }
  };

  return (
    <div className="register-container">
      <h2>Register New User</h2>
      <form onSubmit={handleRegister}>
        <input
          type="text"
          name="firstName"
          value={newUser.firstName}
          onChange={handleChange}
          placeholder="First Name"
          required
        />
        <input
          type="text"
          name="lastName"
          value={newUser.lastName}
          onChange={handleChange}
          placeholder="Last Name"
          required
        />
        <input
          type="email"
          name="email"
          value={newUser.email}
          onChange={handleChange}
          placeholder="Email"
          required
        />
        <input
          type="password"
          name="password"
          value={newUser.password}
          onChange={handleChange}
          placeholder="Password"
          required
        />
        <select
          name="gender"
          value={newUser.gender}
          onChange={handleChange} 
          className = "gender-select"
          required
        >
          <option value="">Select Gender</option>
          <option value="Male">Male</option>
          <option value="Female">Female</option>
          <option value="Other">Other</option>
        </select>

        <input
          type="text"
          name="country"
          value={newUser.country}
          onChange={handleChange}
          placeholder="Country"
          required
        />
        <button type="submit">Register User</button>
      </form>
    </div>
  );
}

export default Register;
