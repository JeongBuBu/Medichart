import "../pages/NotFound.css";
import { Link } from "react-router-dom";
function NotFound() {
  return (
    <div className="all">
      <h1>404 ERROR</h1>
      <p>
        죄송합니다. 페이지를 찾을 수 없습니다.<br></br>
        존재하지 않는 주소를 입력하셨거나,<br></br>
        요청하신 페이지의 주소가 변경, 삭제되어 찾을 수 없습니다.
      </p>

      <Link to="/" className="goHome_Link">
        <div className="goHome">홈으로</div>
      </Link>
    </div>
  );
}
export default NotFound;
