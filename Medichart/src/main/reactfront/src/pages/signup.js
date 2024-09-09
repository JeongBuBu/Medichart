import "../pages/signup.css";
import K_signup from "../K_signup.png";
import N_signup from "../N_signup.png";
import G_signup from "../G_signup.png";
import { Link } from "react-router-dom";

function Signup() {
  // 카카오
  const K_REST_API_KEY = "fce473f392ddd4530306ee0c3531eba0";
  const K_REDIRECT_URI = "http://localhost:8080/login/oauth2/code/kakao";
  const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${K_REST_API_KEY}&redirect_uri=${encodeURIComponent(K_REDIRECT_URI)}&response_type=code`;

  const handlekakaoLogin = () => {
    window.location.href = kakaoURL; //kakaoURL로 이동
  };

  //네이버
  const N_clientID = process.env.REACT_APP_NAVER_CLIENT_ID; //발급 받은 Client ID 입력
  const N_REDIRECT_URI = "http://localhost:3001/naverlogin"; //작성했던 Callback URL 입력
  const stateString = process.env.REACT_APP_NAVER_STATE; //애플리케이션 등록시 받은 시크릿 키 입력
  const NAVER_URL = `https://nid.naver.com/oauth2.0/authorize?client_id=${N_clientID}&response_type=code&redirect_uri=${N_REDIRECT_URI}&state=${stateString}`;

  const handlenaverLogin = () => {
    window.location.href = NAVER_URL; //naverURL로 이동
  };

  //구글
  const G_ClientID = process.env.REACT_APP_GOOGLE_KEY;
  const G_REDIRECT_URI = process.env.REACT_APP_GOOGLE_REDIRECT_URI;
  const G_URL = ` https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=${G_ClientID}&redirect_uri=${G_REDIRECT_URI}&scope=openid%20email%20profile`;

  const handlegoogleLogin = () => {
    window.location.href = G_URL; //구글 URL로 이동
  };
  return (
    <div className="container">
      <h3>회원가입</h3>
      <p className="subtitle">소셜 로그인 및 이메일로 가입할 수 있습니다.</p>
      <p className="line"></p>

      <div className="signupForm ">
        <Link to="/signup/email" className="button">
          <p>이메일로 시작하기</p>
        </Link>
        <img onClick={handlekakaoLogin} src={K_signup}></img>
        <img onClick={handlenaverLogin} src={N_signup}></img>
        <img onClick={handlegoogleLogin} src={G_signup}></img>
      </div>
    </div>
  );
}
export default Signup;
