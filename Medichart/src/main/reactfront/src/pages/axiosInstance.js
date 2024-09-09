import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080', // 기본 URL 설정
});

axiosInstance.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('authToken'); // JWT 가져오기
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`; // JWT를 헤더에 추가
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);


axiosInstance.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response && error.response.status === 401) {
            localStorage.removeItem('authToken'); // JWT가 만료된 경우 localStorage에서 제거합니다.
            window.location.href = '/login'; // 로그인 페이지로 리디렉션합니다.
        }
        return Promise.reject(error);
    }
);

export default axiosInstance;
