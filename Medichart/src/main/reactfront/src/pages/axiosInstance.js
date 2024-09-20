import axios from 'axios';

const axiosInstance = axios.create({
    baseURL: process.env.REACT_APP_API_URL || 'http://localhost:3000',
});

// 요청 인터셉터
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

// 응답 인터셉터
axiosInstance.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response && error.response.status === 401) {
            localStorage.removeItem('authToken'); // JWT가 만료된 경우 localStorage에서 제거
            window.location.href = '/login'; // 로그인 페이지로 리디렉션
        }
        return Promise.reject(error);
    }
);

// 로그인 함수
export const loginUser = async (credentials) => {
    try {
        const response = await axiosInstance.post('/api/auth/login', credentials);
        localStorage.setItem('authToken', response.data.token); // JWT를 local storage에 저장
        // 추가로 사용자 정보를 반환
        return {
            token: response.data.token,
            userId: response.data.userId, // 응답에 userId가 포함되어 있어야 함
            name: response.data.name // 응답에 사용자 이름이 포함되어 있어야 함
        };
    } catch (error) {
        throw error;
    }
};

// 로그아웃 함수
export const logoutUser = () => {
    localStorage.removeItem('authToken'); // local storage에서 JWT 삭제
    window.location.href = '/login'; // 로그인 페이지로 리디렉션
};

export default axiosInstance;
