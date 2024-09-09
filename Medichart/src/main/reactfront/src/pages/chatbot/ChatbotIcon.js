import React, { useContext } from "react";
import { ChatbotContext } from "../../contexts/ChatbotContext";

const ChatbotIcon = () => {
  const { toggleChatbot } = useContext(ChatbotContext);

  return (
    <div
      style={{
        position: "fixed",
        bottom: "20px",
        right: "20px",
        cursor: "pointer",
      }}
      onClick={toggleChatbot}
    >
      <img
        src="/chatbot.png"
        alt=""
        style={{
          width: "70px",
          height: "70px",
          borderRadius: "50%",
          marginRight: "7px",
        }}
      />
    </div>
  );
};

export default ChatbotIcon;
