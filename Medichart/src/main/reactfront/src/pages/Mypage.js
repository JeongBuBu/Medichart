import React, { useEffect, useState } from 'react';
import axiosInstance from './axiosInstance'; // 올바른 경로로 수정
import "../pages/Mypage.css";

const Mypage = () => {
  const [profile, setProfile] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const response = await axiosInstance.get('/api/user/profile');
        setProfile(response.data);
      } catch (err) {
        console.error('마이페이지 로드 오류:', err.response ? err.response.data : err.message);
        setError('마이페이지 정보를 불러오는데 실패했습니다.');
      }
    };

    fetchProfile();
  }, []);

  if (error) {
    return <p style={{ color: 'red' }}>{error}</p>;
  }

  if (!profile) {
    return <p>로딩 중...</p>;
  }

  return (
      <div className="mypage-container">
        <h2>마이페이지</h2>

        <div className="profile-section">
          <h3>계정 정보</h3>
          <div className="profile-info">
            <div className="info">
              <label>이메일:</label>
              <span>{profile.email}</span>
            </div>
            <div className="info">
              <label>이름:</label>
              <span>{profile.name}</span>
            </div>
            <div className="info">
              <label>성별:</label>
              <span>{profile.gender}</span>
            </div>
            <div className="info">
              <label>생일:</label>
              <span>{profile.dateOfBirth}</span>
            </div>
            <div className="info">
              <label>가입일:</label>
              <span>{new Date(profile.createdAt).toLocaleDateString()}</span>
            </div>
            <button className="edit-button">프로필 변경</button>
          </div>
        </div>

        <div className="social-login-section">
          <h3>소셜 로그인 관리</h3>
          <div className="social-buttons">
            <button className="social-button">구글 연동하기</button>
            <button className="social-button">네이버 연동하기</button>
            <button className="social-button">카카오 연동하기</button>
          </div>
        </div>

        <div className="delete-account-section">
          <h3>회원 탈퇴</h3>
          <p>회원 탈퇴시 모든 정보가 삭제됩니다.</p>
          <button className="delete-button">회원 탈퇴</button>
        </div>
      </div>
  );
};

export default Mypage;
