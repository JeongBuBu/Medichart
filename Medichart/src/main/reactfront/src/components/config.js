import { createChatBotMessage } from 'react-chatbot-kit';
import '../pages/chatbot/chatbot.css';

const config = {
    initialMessages: [
        createChatBotMessage('안녕하세요! 궁금한 내용을 입력해주세요.'),
    ],
    customStyles: {
        botMessageBox: {
            backgroundColor: '#1a76d1',
        },
        chatButton: {
            backgroundColor: '#1a76d1',
            color: 'black',
        },
    },
    customComponents: {
        header: () => (
            <div
                style={{
                    backgroundColor: '#1a76d1',
                    padding: '3px',
                    borderRadius: '3px 3px 0 0',
                    color: 'white',
                    display: 'flex',
                    alignItems: 'center',
                    height: '50px',
                    fontSize: '18px',
                }}
            >
                <img
                    src='/MediChart_clear.png'
                    alt='MediChart Logo'
                    style={{
                        marginLeft: '5px',
                        marginRight: '5px',
                        display: 'flex',
                        alignItems: 'center',
                        height: '30px',
                    }}
                />
                MediChart
            </div>
        ),
        botAvatar: (props) => (
            <div {...props} style={{ display: 'flex', alignItems: 'center' }}>
                <img
                    src='/chatbotA.png'
                    alt='Bot Avatar'
                    style={{ height: '40px', width: '40px', borderRadius: '50%' }}
                />
            </div>
        ),
        userAvatar: (props) => <div {...props} />,
    },
};

export default config;
