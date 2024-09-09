import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import "./index.css";
import { BrowserRouter } from "react-router-dom";

// 카카오 SDK 스크립트 로드
const loadKakaoSdk = () => {
    return new Promise((resolve) => {
        const existingScript = document.getElementById('kakao-sdk');
        if (!existingScript) {
            const script = document.createElement('script');
            script.src = 'https://developers.kakao.com/sdk/js/kakao.js';
            script.id = 'kakao-sdk';
            script.onload = () => {
                resolve();
            };
            document.head.appendChild(script);
        } else {
            resolve();
        }
    });
};

loadKakaoSdk().then(() => {
    const KAKAO_API_KEY = "fce473f392ddd4530306ee0c3531eba0";
    if (window.Kakao && !window.Kakao.isInitialized()) {
        window.Kakao.init(KAKAO_API_KEY);
    }

    const root = ReactDOM.createRoot(document.getElementById("root"));
    root.render(
        <React.StrictMode>
            <BrowserRouter>
                <App />
            </BrowserRouter>
        </React.StrictMode>
    );
});
