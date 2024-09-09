const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const dialogflow = require('@google-cloud/dialogflow');
const uuid = require('uuid');
const path = require('path');

// Express 애플리케이션 생성
const app = express();
const port = 3001; // 포트를 3001로 설정

// JSON 파싱 미들웨어
app.use(cors());
app.use(bodyParser.json());

// 서비스 계정 키 파일 경로를 환경 변수에서 가져오기
const keyFile = process.env.GOOGLE_APPLICATION_CREDENTIALS || path.resolve(__dirname, '..', 'resources', 'medichart-428805-42763aa8030c.json');

// Dialogflow 클라이언트 설정
const sessionClient = new dialogflow.SessionsClient({ keyFilename: keyFile });
const projectId = 'medichart-428805';
const sessionId = uuid.v4();

// Dialogflow 세션 경로 설정
const sessionPath = sessionClient.projectAgentSessionPath(projectId, sessionId);

// 챗봇 엔드포인트
app.post('/chatbot', async (req, res) => {
    const message = req.body.message;

    // Check if the message is valid
    if (!message || typeof message !== 'string' || message.trim() === '') {
        return res.status(400).send('Invalid message');
    }

    const sessionPath = sessionClient.projectAgentSessionPath(projectId, sessionId);

    const request = {
        session: sessionPath,
        queryInput: {
            text: {
                text: message,
                languageCode: 'ko-KR', // Ensure this matches your Dialogflow configuration
            },
        },
    };

    try {
        const responses = await sessionClient.detectIntent(request);
        const result = responses[0].queryResult;

        res.json({ response: result.fulfillmentText });
    } catch (error) {
        console.error('Dialogflow Error:', error);
        res.status(500).send('Dialogflow request failed');
    }
});


// 정적 파일 서빙
app.use(express.static(path.resolve(__dirname, '..', 'resources', 'templates')));

// 기본 경로 설정
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'resources', 'templates', 'chatbot.html'));
});

// 서버 시작
app.listen(port, () => {
    console.log(`Server is running on http://localhost:${port}`);
});
