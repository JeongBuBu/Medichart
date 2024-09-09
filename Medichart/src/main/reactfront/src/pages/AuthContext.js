import React, { createContext, useState, useEffect } from 'react';
import axiosInstance from './axiosInstance'; // axiosInstance 파일 경로를 지정합니다.

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState(null); // user 상태 추가

    useEffect(() => {
        const token = localStorage.getItem('authToken');
        if (token) {
            fetchUserProfile(token);
        } else {
            handleLogout();
        }
    }, []);

    const fetchUserProfile = async (token) => {
        try {
            const response = await axiosInstance.get('/api/user/profile');
            const data = response.data;
            if (data && data.email) {
                setIsLoggedIn(true);
                setUser(data); // 사용자 정보 설정
            } else {
                handleLogout();
            }
        } catch (error) {
            console.error('사용자 프로필 요청 중 오류:', error);
            handleLogout();
        }
    };

    const login = (token, user) => {
        setIsLoggedIn(true);
        setUser(user);
        localStorage.setItem('authToken', token);
    };

    const handleLogout = () => {
        setIsLoggedIn(false);
        setUser(null);
        localStorage.removeItem('authToken');
    };

    return (
        <AuthContext.Provider value={{ user, isLoggedIn, login, logout: handleLogout }}>
            {children}
        </AuthContext.Provider>
    );
};
