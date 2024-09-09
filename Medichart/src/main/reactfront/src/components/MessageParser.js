class MessageParser {
  constructor(actionProvider, state) {
    this.actionProvider = actionProvider;
    this.state = state;
  }

  async parse(message) {
    console.log(message);

    // 서버로 메시지 전송
    try {
      const response = await fetch('http://localhost:3001/chatbot', { // 서버 URL을 환경에 맞게 변경
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ message }),
      });

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      const data = await response.json();
      const botMessage = data.response; // 서버의 응답을 처리합니다.

      this.actionProvider.handleServerResponse(botMessage); // 응답을 ActionProvider에 전달합니다.
    } catch (error) {
      console.error('Error:', error);
    }
  }
}

export default MessageParser;
