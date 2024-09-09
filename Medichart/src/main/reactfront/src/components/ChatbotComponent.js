import React, { useContext } from 'react';
import Chatbot from 'react-chatbot-kit';
import 'react-chatbot-kit/build/main.css';
import { ChatbotContext } from '../contexts/ChatbotContext';
import ActionProvider from './ActionProvider';
import MessageParser from './MessageParser';
import config from './config';

const ChatbotComponent = () => {
    const { isChatbotVisible } = useContext(ChatbotContext);

    if (!isChatbotVisible) return null;

    return (
        <div
            style={{
                position: 'fixed',
                bottom: '20px',
                right: '80px',
                width: '300px',
                height: '500px',
                zIndex: 1000,
                marginBottom: "105px",
            }}
        >
            <Chatbot
                config={config}
                messageParser={MessageParser}
                actionProvider={ActionProvider}
            />
        </div>
    );
};

export default ChatbotComponent;
