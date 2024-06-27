import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Login from './component/Login';
import Home from './component/Home';
import Register from './component/Register';
import LandingPage from './component/LandingPage';
import ChatPage from './component/ChatPage'; // Adjust the import path as necessary


const App = () => {
    const [token, setToken] = useState(localStorage.getItem('token') || '');

    // Update local storage when token changes
    useEffect(() => {
        localStorage.setItem('token', token);
    }, [token]);

    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login setToken={setToken} />} />
                <Route path="/home" element={token ? <Home token={token} /> : <Navigate to="/login" />} />
                <Route path="/home/chat" element={token ? <ChatPage /> : <Navigate to="/login" />} />
                <Route path="/register" element={token ? <Register token={token} /> : <Navigate to="/login" />} />
                <Route path="*" element={<Navigate to="/login" />} />
                <Route path="/" element={<LandingPage />} />
            </Routes>
        </Router>
    );
};

export default App;
