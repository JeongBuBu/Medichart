import React, { createContext, useState } from "react";

export const ChatbotContext = createContext();

export const ChatbotProvider = ({ children }) => {
  const [isChatbotVisible, setChatbotVisible] = useState(false);

  const toggleChatbot = () => {
    setChatbotVisible((prev) => !prev);
  };

  return (
    <ChatbotContext.Provider value={{ isChatbotVisible, toggleChatbot }}>
      {children}
    </ChatbotContext.Provider>
  );
};
