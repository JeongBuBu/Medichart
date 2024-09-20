import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userId, setUserId] = useState(null);
    const [username, setUsername] = useState(''); // 사용자 이름 상태 추가

    useEffect(() => {
        fetch('http://localhost:3000/profile', { credentials: 'include' })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Fetched profile data:', data);
                if (data.username) {
                    setIsLoggedIn(true);
                    setUserId(data.userId); // 데이터에 userId가 포함되어 있어야 함
                    setUsername(data.username);
                } else {
                    setIsLoggedIn(false);
                    setUserId(null);
                    setUsername('');
                }
            })
            .catch(error => {
                console.error('Fetch error:', error);
                setIsLoggedIn(false);
                setUserId(null);
                setUsername('');
            });
    }, []);

    const login = (userId, username) => {
        setIsLoggedIn(true);
        setUserId(userId);
        setUsername(username);
    };

    const logout = () => {
        fetch('http://localhost:3000/logout', {
            method: 'POST',
            credentials: 'include'
        })
            .then(() => {
                setIsLoggedIn(false);
                setUserId(null);
                setUsername('');
            })
            .catch(err => console.error('Failed to log out:', err));
    };

    return (
        <AuthContext.Provider value={{ isLoggedIn, userId, username, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};
