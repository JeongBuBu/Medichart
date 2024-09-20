import "../pages/login.css";
import { Link } from "react-router-dom";
import axios from 'axios';
import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "./AuthContext";

// SNS 로그인 관련 이미지들
import kakaologo from "../kakaotalk_sharing_btn_small.png";
import naverlogo from "../naverlogo.png";
import googlelogo from "../btn_google.svg";

function Login() {
    const { login } = useContext(AuthContext);
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        email: '',
        password: ''
    });
    const [errorMessage, setErrorMessage] = useState('');

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/api/auth/login', formData, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            const { token } = response.data;

            if (token) {
                login(token);
                localStorage.setItem('authToken', token);
                navigate('/');
            } else {
                setErrorMessage('로그인 실패: 서버에서 유효한 토큰을 반환하지 않았습니다.');
            }
        } catch (error) {
            console.error('로그인 오류:', error.response ? error.response.data : error.message);

            if (error.response) {
                setErrorMessage(`로그인 실패: ${error.response.data}`);
            } else {
                setErrorMessage('로그인 실패: 네트워크 오류 또는 서버 문제');
            }
        }
    };

    // SNS 로그인 URL 설정
    const K_REST_API_KEY = "db8efd7a5919e4c619cf6e676b5189fa";
    const K_REDIRECT_URI = "http://localhost:8080/login/oauth2/code/kakao";
    const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${K_REST_API_KEY}&redirect_uri=${encodeURIComponent(K_REDIRECT_URI)}&response_type=code&scope=profile_nickname,account_email`;

    const handleKakaoLogin = () => {
        window.location.href = kakaoURL;
    };

    const N_CLIENT_ID = "J9e7ZObddUCfs_4uICkI";
    const N_REDIRECT_URI = "http://localhost:8080/login/oauth2/code/naver";
    const stateString = generateRandomState();
    const NAVER_URL = `https://nid.naver.com/oauth2.0/authorize?client_id=${N_CLIENT_ID}&response_type=code&redirect_uri=${encodeURIComponent(N_REDIRECT_URI)}&state=${stateString}`;

    const handleNaverLogin = () => {
        window.location.href = NAVER_URL;
    };

    function generateRandomState() {
        return Math.random().toString(36).substring(2);
    }

    const G_ClientID = "458553321878-g01fc7i4e4rc1v4ppf3lncj3emmcpqav.apps.googleusercontent.com";
    const G_REDIRECT_URI = "http://localhost:8080/login/oauth2/code/google";
    const G_URL = `https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=${G_ClientID}&redirect_uri=${encodeURIComponent(G_REDIRECT_URI)}&scope=openid%20email%20profile`;

    const handleGoogleLogin = () => {
        window.location.href = G_URL;
    };

    return (
        <div className="container">
            <h3>로그인</h3>
            <p style={{ color: "red", margin: "0 0 30px 9px" }}>
                로그인 후 이용해주세요
            </p>
            <form onSubmit={handleSubmit}>
                <div className="loginForm">
                    <input
                        type="email"
                        id="email"
                        name="email"
                        placeholder="이메일을 입력하세요"
                        className="loginInput"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />
                    <input
                        type="password"
                        id="password"
                        name="password"
                        placeholder="비밀번호를 입력하세요"
                        className="loginInput"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                </div>

                {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}

                <div className="loginForm_bottom">
                    <input
                        type="submit"
                        value="이메일로 로그인"
                        className="button"
                    />

                    <div className="section_find">
                        <p style={{ fontSize: "small", paddingTop: "20px", marginLeft: "10px" }}>
                            <Link to="/login/signup" style={{ color: "black", fontSize: "16px" }}>회원가입</Link>
                        </p>
                        <p style={{ color: "grey", display: "inline", borderBottom: "1px solid grey", marginLeft: "10px" }}>
                            SNS 로그인
                        </p>
                        <div className="SNS">
                            <img onClick={handleNaverLogin} src={naverlogo} alt="Naver Login" style={{ cursor: 'pointer' }} />
                            <img onClick={handleKakaoLogin} src={kakaologo} alt="Kakao Login" style={{ cursor: 'pointer' }} />
                            <img onClick={handleGoogleLogin} src={googlelogo} alt="Google Login" style={{ cursor: 'pointer' }} />
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default Login;
