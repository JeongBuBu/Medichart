import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userId, setUserId] = useState(null);
    const [username, setUsername] = useState(''); // 사용자 이름 상태 추가

    useEffect(() => {
        fetch('http://localhost:3000/api/user/profile', { credentials: 'include' })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Fetched profile data:', data);
                if (data.name) {
                    setIsLoggedIn(true);
                    setUserId(data.userId); // 데이터에 userId가 포함되어 있어야 함
                    setUsername(data.name);
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

    const login = (userId, name) => {
        setIsLoggedIn(true);
        setUserId(userId);
        setUsername(name);
    };

    const logout = () => {
        fetch('http://localhost:3000/api/auth/logout', {
            method: 'POST',
            credentials: 'include'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Logout failed');
                }
                // 로그아웃 성공 후 상태 초기화
                setIsLoggedIn(false);
                setUserId(null);
                setUsername('');
                // 추가적으로 필요 시 로컬 스토리지나 세션 스토리지에서 정보 제거
                localStorage.removeItem('userId'); // 예시로 userId를 로컬 스토리지에서 제거
                // 필요 시, 다른 저장소에서의 정보 제거도 여기에 추가
            })
            .catch(err => console.error('Failed to log out:', err));
    };

    return (
        <AuthContext.Provider value={{ isLoggedIn, userId, username, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};