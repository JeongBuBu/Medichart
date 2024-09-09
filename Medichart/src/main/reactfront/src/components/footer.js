import React, { useState } from "react";
import styles from "./footer.module.css";
import logo from "../MediChart_clear.png";
import PrivacyModal from "../pages/PrivacyModal.js";
// import { Link } from "react-router-dom";

function Footer() {
  // if (window.location.pathname === "/admin") return null;

  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };
  return (
    <div className={styles.footer}>
      <div className={styles.contents}>
        <div className={styles.title}>
          <img className={styles.logo} src={logo} alt="MediChart Logo"></img>
          <p>MediChart</p>
        </div>

        <div className={styles.middle}>
          <p>Copyright © 2024. MediChart All rights reserved</p>
          {/* <Link to="/privacy-policy" className={styles.privacyLink}>
            개인정보처리방침
          </Link> */}
          <span onClick={openModal} className={styles.privacyLink}>
            개인정보처리방침
          </span>
        </div>
      </div>
      {/* isModalOpen이 true일 때만 PrivacyModal을 렌더링 */}
      {isModalOpen && (
        <PrivacyModal isOpen={isModalOpen} onClose={closeModal} />
      )}
      {/* <PrivacyModal isOpen={isModalOpen} onClose={closeModal} /> */}
    </div>
  );
}

export default Footer;
