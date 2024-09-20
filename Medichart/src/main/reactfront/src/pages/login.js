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
                            <a href="http://localhost:8080/oauth2/authorization/naver" style={{ cursor: 'pointer' }}>
                                <img src={naverlogo} alt="Naver Login" />
                            </a>
                            <a href="http://localhost:8080/oauth2/authorization/kakao" style={{ cursor: 'pointer' }}>
                                <img src={kakaologo} alt="Kakao Login" />
                            </a>
                            <a href="http://localhost:8080/oauth2/authorization/google" style={{ cursor: 'pointer' }}>
                                <img src={googlelogo} alt="Google Login" />
                            </a>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default Login;
