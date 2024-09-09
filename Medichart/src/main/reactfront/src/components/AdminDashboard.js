import React, { useState, useContext, useEffect } from 'react';
import { WebSocketContext } from './WebSocketProvider';

const AdminDashboard = () => {
    const [messages, setMessages] = useState([]);
    const { adminSocket } = useContext(WebSocketContext);

    useEffect(() => {
        if (adminSocket) {
            adminSocket.onmessage = (event) => {
                try {
                    const message = JSON.parse(event.data);
                    setMessages((prevMessages) => [...prevMessages, message]);
                } catch (error) {
                    console.error('메시지 파싱 에러:', error);
                }
            };

            adminSocket.onclose = () => {
                console.log('웹소켓 연결 종료');
            };

            adminSocket.onerror = (error) => {
                console.error('웹소켓 에러:', error);
            };
        }
    }, [adminSocket]);

    return (
        <div>
            <h2>관리자 대시보드</h2>
            <div>
                {messages.length > 0 ? (
                    messages.map((msg, index) => (
                        <p key={index}>{msg.title}: {msg.content}</p>
                    ))
                ) : (
                    <p>메시지가 없습니다.</p>
                )}
            </div>
        </div>
    );
};

export default AdminDashboard;