import React, { useContext, useEffect } from 'react';
import { AuthContext } from '../pages/AuthContext';
import { useNavigate } from 'react-router-dom';

const KakaoLoginCallback = () => {
    const { login } = useContext(AuthContext);
    const navigate = useNavigate();

    useEffect(() => {
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        const code = urlParams.get('code');

        if (code) {
            // 카카오 API로 로그인 처리
            window.Kakao.Auth.login({
                success: function(authObj) {
                    console.log('카카오 로그인 성공:', authObj);
                    // 로그인 성공 시 상태 업데이트
                    login('사용자 이름'); // 실제 사용자 이름으로 변경 필요
                    navigate('/');
                },
                fail: function(err) {
                    console.error('카카오 로그인 실패:', err);
                }
            });
        }
    }, [login, navigate]);

    return <div>로그인 중...</div>;
};

export default KakaoLoginCallback;
