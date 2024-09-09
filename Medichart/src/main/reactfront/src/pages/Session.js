const express = require('express');
const session = require('express-session');
const bodyParser = require('body-parser');
const cors = require('cors');
const app = express();

// CORS 설정 (클라이언트가 다른 포트에서 실행될 경우 필요)
app.use(cors({
    origin: 'http://localhost:3000', // 클라이언트 애플리케이션의 URL
    credentials: true // 쿠키를 함께 전송
}));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// 세션 설정
app.use(session({
    secret: 'your-secret-key', // 비밀 키
    resave: false, // 세션이 수정되지 않아도 매 요청마다 세션을 다시 저장
    saveUninitialized: false, // 초기화되지 않은 세션을 저장하지 않음
    cookie: { secure: false, maxAge: 24 * 60 * 60 * 1000 } // 쿠키 만료 시간 설정 (1일)
}));

// 사용자 인증 데이터 (예시 데이터)
const users = {
    'user@example.com': 'password123'
};

// 로그인 처리
app.post('/login', (req, res) => {
    const { email, password } = req.body;

    if (users[email] && users[email] === password) {
        req.session.userId = email; // 세션에 사용자 이메일 저장
        res.json({ message: 'Logged in', userId: email });
    } else {
        res.status(401).json({ message: 'Invalid credentials' });
    }
});

// 로그아웃 처리
app.post('/logout', (req, res) => {
    req.session.destroy((err) => {
        if (err) {
            return res.status(500).json({ message: 'Failed to log out' });
        }
        res.clearCookie('connect.sid'); // 쿠키 삭제
        res.json({ message: 'Logged out' });
    });
});

// 세션 상태 확인
app.get('/profile', (req, res) => {
    if (req.session.userId) {
        res.json({ userId: req.session.userId });
    } else {
        res.status(401).json({ message: 'Not logged in' });
    }
});

// 서버 시작
app.listen(3001, () => {
    console.log('Server running on port 3001');
});
