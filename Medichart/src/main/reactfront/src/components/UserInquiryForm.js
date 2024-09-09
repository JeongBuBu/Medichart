import React, { useState, useContext } from 'react';
import { WebSocketContext } from './WebSocketProvider';

const UserInquiryForm = () => {
    const [inquiry, setInquiry] = useState('');
    const { userSocket } = useContext(WebSocketContext);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (userSocket && userSocket.readyState === WebSocket.OPEN) {
            const message = JSON.stringify({ title: 'New Inquiry', content: inquiry });
            userSocket.send(message);
            setInquiry('');
        } else {
            console.error('WebSocket is not open.');
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <textarea
                value={inquiry}
                onChange={(e) => setInquiry(e.target.value)}
                placeholder="문의사항을 입력하세요"
                required
            />
            <button type="submit">전송</button>
        </form>
    );
};

export default UserInquiryForm;