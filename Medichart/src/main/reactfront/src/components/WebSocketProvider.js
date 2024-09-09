import React, { createContext, useState, useEffect } from 'react';

export const WebSocketContext = createContext();

export const WebSocketProvider = ({ children }) => {
    const [userSocket, setUserSocket] = useState(null);
    const [adminSocket, setAdminSocket] = useState(null);

    useEffect(() => {
        const connectUserSocket = () => {
            const ws = new WebSocket('ws://localhost:8080/user');

            ws.onopen = () => console.log('User WebSocket connection established');
            ws.onmessage = (event) => console.log('User WebSocket message received:', event.data);
            ws.onerror = (error) => console.error('User WebSocket error:', error);
            ws.onclose = () => console.log('User WebSocket connection closed');

            setUserSocket(ws);
            return ws;
        };

        const connectAdminSocket = () => {
            const ws = new WebSocket('ws://localhost:8080/admin');

            ws.onopen = () => console.log('Admin WebSocket connection established');
            ws.onmessage = (event) => console.log('Admin WebSocket message received:', event.data);
            ws.onerror = (error) => console.error('Admin WebSocket error:', error);
            ws.onclose = () => console.log('Admin WebSocket connection closed');

            setAdminSocket(ws);
            return ws;
        };

        const userWs = connectUserSocket();
        const adminWs = connectAdminSocket();

        return () => {
            if (userWs) userWs.close();
            if (adminWs) adminWs.close();
        };
    }, []);

    return (
        <WebSocketContext.Provider value={{ userSocket, adminSocket }}>
            {children}
        </WebSocketContext.Provider>
    );
};
