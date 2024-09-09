import React, { useState, useContext, useEffect } from "react";
import FAQ from "./FAQ";
import Inquiry from "./Inquiry"; // 1:1 문의
import Notice from "./Notice"; // 공지사항
import InquiryList from "./InquiryList"; // 문의 내역
import { WebSocketContext } from "../components/WebSocketProvider"; // 웹소켓 컨텍스트 가져오기
import "../pages/CustomerService.css";
import { AuthContext } from "./AuthContext";

function CustomerService() {
  const [activeTab, setActiveTab] = useState("faq");
  const [messages, setMessages] = useState([]);
  const { isLoggedIn, username } = useContext(AuthContext);
  const { userSocket, adminSocket } = useContext(WebSocketContext);

  useEffect(() => {
    if (adminSocket) {
      const handleAdminMessage = (event) => {
        try {
          const message = JSON.parse(event.data);
          setMessages((prevMessages) => [
            ...prevMessages,
            `${message.title}: ${message.content}`,
          ]);
        } catch (error) {
          console.error("Admin WebSocket message parsing error:", error);
        }
      };

      adminSocket.onmessage = handleAdminMessage;
      adminSocket.onopen = () => {};
      adminSocket.onclose = () => {};
      adminSocket.onerror = (error) => console.error("Admin WebSocket error:", error);

      return () => {
        adminSocket.close();
      };
    }
  }, [adminSocket]);

  useEffect(() => {
    if (userSocket) {
      userSocket.onopen = () => {};
      userSocket.onclose = () => {};
      userSocket.onerror = (error) => console.error("User WebSocket error:", error);

      return () => {
        userSocket.close();
      };
    }
  }, [userSocket]);

  const handleSubmitInquiry = (title, content) => {
    if (userSocket && userSocket.readyState === WebSocket.OPEN) {
      const inquiryMessage = JSON.stringify({
        title,
        content,
        username, // 사용자 이름 포함
      });
      userSocket.send(inquiryMessage);
      console.log('Inquiry sent:', inquiryMessage);
    } else {
      console.error("User WebSocket is not open or has an error.");
    }
  };

  return (
      <div className="customer_top">
        <main>
          <div className="banner"></div>
          <nav className="tabs">
            <h2
                onClick={() => setActiveTab("faq")}
                className={activeTab === "faq" ? "active" : ""}
            >
              자주 묻는 질문
            </h2>
            <h2
                onClick={() => setActiveTab("inquiry")}
                className={activeTab === "inquiry" ? "active" : ""}
            >
              1:1 문의
            </h2>
            <h2
                onClick={() => setActiveTab("notice")}
                className={activeTab === "notice" ? "active" : ""}
            >
              공지사항
            </h2>
          </nav>
          {activeTab === "faq" && <FAQ />}
          {activeTab === "notice" && <Notice />}
          {activeTab === "inquiry" && (
              <>
                <Inquiry
                    addInquiry={(title, content) => handleSubmitInquiry(title, content)}
                    username={username} // username 전달
                />
                <InquiryList username={username} /> {/* 1:1 문의 탭에서만 문의 내역을 보여줌 */}
              </>
          )}
          <div className="messages">
            {messages.map((msg, index) => (
                <div key={index}>{msg}</div>
            ))}
          </div>
        </main>
      </div>
  );
}

export default CustomerService;
