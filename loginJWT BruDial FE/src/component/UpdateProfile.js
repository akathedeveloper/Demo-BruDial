import React, { useState, useEffect } from 'react';
import axios from '../api/axios';
import { useNavigate } from 'react-router-dom';
import '../css/UpdateProfile.css';

const UpdateProfile = () => {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        gender: '',
        country: '',
        password: '',
        currentPassword: ''
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [emailError, setEmailError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserDetails = async () => {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    navigate('/login');
                    return;
                }
                const response = await axios.get('/home', {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                const { firstName, lastName, email, gender, country } = response.data.data;
                setFormData({ firstName, lastName, email, gender, country, password: '', currentPassword: '' });
            } catch (error) {
                console.error('Failed to fetch user details', error);
                navigate('/login');
            }
        };

        fetchUserDetails();
    }, [navigate]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === 'email') {
            setEmailError('You cannot change the email address');
            return;
        }
        setFormData({ ...formData, [name]: value });
        if (name === 'email' && value === formData.email) {
            setEmailError(null);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        try {
            const token = localStorage.getItem('token');
            await axios.put('/home/update', formData, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setLoading(false);
            navigate('/home');
        } catch (error) {
            setError('Failed to update profile');
            setLoading(false);
        }
    };

    return (
        <div className="update-profile-container">
            <h2>Update Profile</h2>
            {error && <div className="error-message">{error}</div>}
            <form onSubmit={handleSubmit}>
                <label>
                    First Name:
                    <input
                        type="text"
                        name="firstName"
                        value={formData.firstName}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Last Name:
                    <input
                        type="text"
                        name="lastName"
                        value={formData.lastName}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Email:
                    <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        readOnly
                        required
                    />
                </label>
                {emailError && <div className="error-message">{emailError}</div>}
                <label>
                    Gender:
                    <select 
                        name="gender"
                        value={formData.gender}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Select Gender</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                    </select>
                </label>
                <label>
                    Country:
                    <input
                        type="text"
                        name="country"
                        value={formData.country}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Current Password:
                    <input
                        type="password"
                        name="currentPassword"
                        value={formData.currentPassword}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    New Password:
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                </label>
                <button type="submit" disabled={loading}>
                    {loading ? 'Updating...' : 'Update Profile'}
                </button>
            </form>
        </div>
    );
};

export default UpdateProfile;