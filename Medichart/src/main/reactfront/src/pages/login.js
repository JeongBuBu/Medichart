import "../pages/login.css";
import { Link } from "react-router-dom";
import axios from 'axios';
import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "./AuthContext";

// SNS 로그인 관련 이미지들
// import kakaologo from "../kakaotalk_sharing_btn_small.png";
// import naverlogo from "../naverlogo.png";
// import googlelogo from "../btn_google.svg";

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

            // 응답 데이터 확인
            const { token } = response.data;

            if (token) {
                // 로그인 상태를 관리하는 AuthContext에 토큰을 저장
                login(token);

                // 토큰을 localStorage에 저장 (옵션, 필요시 사용)
                localStorage.setItem('authToken', token);

                // 로그인 성공 후 홈 페이지로 이동
                navigate('/');
            } else {
                // 토큰이 없으면 로그인 실패 메시지
                setErrorMessage('로그인 실패: 서버에서 유효한 토큰을 반환하지 않았습니다.');
            }
        } catch (error) {
            // 오류 로그 출력
            console.error('로그인 오류:', error.response ? error.response.data : error.message);

            // 서버 오류 또는 네트워크 오류 메시지 처리
            if (error.response) {
                setErrorMessage(`로그인 실패: ${error.response.data}`);
            } else {
                setErrorMessage('로그인 실패: 네트워크 오류 또는 서버 문제');
            }
        }
    };
    // SNS 로그인 URL 설정
    /*
    const K_REST_API_KEY = "fce473f392ddd4530306ee0c3531eba0";
    const K_REDIRECT_URI = "http://localhost:8080/login/oauth2/code/kakao";
    const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${K_REST_API_KEY}&redirect_uri=${encodeURIComponent(K_REDIRECT_URI)}&response_type=code&scope=profile_nickname,account_email`;

    const handlekakaoLogin = () => {
        window.location.href = kakaoURL;
    };

    const N_CLIENT_ID =  "J9e7ZObddUCfs_4uICkI"; // 환경 변수에서 가져옴
    const N_REDIRECT_URI = "http://localhost:8080/login/oauth2/code/naver"; // 환경 변수에서 가져옴
    const stateString = generateRandomState(); // 랜덤 상태 생성 함수 사용
    const NAVER_URL = `https://nid.naver.com/oauth2.0/authorize?client_id=${N_CLIENT_ID}&response_type=code&redirect_uri=${encodeURIComponent(N_REDIRECT_URI)}&state=${stateString}`;

    const handlenaverLogin = () => {
        window.location.href = NAVER_URL;
    };

    // 랜덤 상태 문자열 생성 함수
    function generateRandomState() {
        return Math.random().toString(36).substring(2);
    }

    const G_ClientID = "709796471451-hdg13q22jmbruh79om4k0vb4t4b2plmp.apps.googleusercontent.com";
    const G_REDIRECT_URI = "http://localhost:8080/login/oauth2/code/google";

    const G_URL = `https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=${G_ClientID}&redirect_uri=${encodeURIComponent(G_REDIRECT_URI)}&scope=openid%20email%20profile`;

    const handlegoogleLogin = () => {
        window.location.href = G_URL;
    };
    */

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
                            {/* SNS 로그인 버튼들 주석 처리 */}
                            {/* <img onClick={handlenaverLogin} src={naverlogo} alt="Naver Login" />
                            <img onClick={handlekakaoLogin} src={kakaologo} alt="Kakao Login" />
                            <img onClick={handlegoogleLogin} src={googlelogo} alt="Google Login" /> */}
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default Login;
