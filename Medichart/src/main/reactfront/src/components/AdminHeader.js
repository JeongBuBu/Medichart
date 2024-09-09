// AdminHeader.js
import styles from "./header.module.css";
import { Link } from "react-router-dom";
import logo from "../MediChart_clear.png";
import { useState } from "react";
function AdminHeader() {
  const [dropdownOpen, setDropdownOpen] = useState([false, false]);

  const toggleDropdown = (index) => {
    const newDropdownOpen = dropdownOpen.map((isOpen, i) =>
      i === index ? !isOpen : false
    );
    setDropdownOpen(newDropdownOpen);
  };

  return (
    <header className={styles.header}>
      <div className={styles.contents}>
        <div>
          <Link to="/" className={styles.link1}>
            <img className={styles.logo} src={logo} alt="Logo"></img>
            <p>MediChart</p>
          </Link>
        </div>

        <nav className={styles.navigation}>
          <ul>
            <li onClick={() => toggleDropdown(0)}>
              공지사항 관리
              <span
                  className={`${styles.triangle} ${
                  dropdownOpen[0] ? styles.open : ""
                }`}
              ></span>
              {dropdownOpen[0] && (
                <ul className={styles.dropdown}>
                  <li>
                    <Link to="/admin/noticeList" className={styles.link}>
                      - 공지사항 목록
                    </Link>
                  </li>
                  <li>
                    <Link to="/admin/notice/new" className={styles.link}>
                      - 공지사항 작성
                    </Link>
                  </li>
                </ul>
              )}
            </li>
            <li>
              <Link to="/admin/inquiryList" className={styles.link}>
                문의사항 관리
              </Link>
            </li>
            <li>
              <Link to="/admin/main" className={styles.link}>
                반갑습니다 관리자님
              </Link>
            </li>
          </ul>
        </nav>
      </div>
    </header>
  );
}
export default AdminHeader; // 함수 컴포넌트를 export default로 내보냄
