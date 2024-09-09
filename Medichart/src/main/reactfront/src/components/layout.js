import React from "react";
import Header from "./header";
import Footer from "./footer";
import styles from "./layout.module.css";

import ChatbotIcon from "../pages/chatbot/ChatbotIcon";
import ChatbotComponent from "./ChatbotComponent";

const Layout = (props: { children: React.ReactNode }) => {
  return (
    <div className={styles.layout}>
      <Header />
      <main>{props.children}</main>
      <Footer />
      <ChatbotIcon /> {/* Chatbot 아이콘 추가 */}
      <ChatbotComponent /> {/* Chatbot 컴포넌트 추가 */}
    </div>
  );
};

export default Layout;
