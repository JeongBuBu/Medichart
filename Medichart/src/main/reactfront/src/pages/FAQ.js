import React, { useState } from "react";
import "../pages/FAQ.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faQ, faA } from "@fortawesome/free-solid-svg-icons";

const questions = [
  {
    question: "메디차트는 무슨 사이트인지 궁금합니다.",
    answer:
      "메디차트는 사용자의 건강을 위해 종합적인 서비스를 제공하는 플랫폼입니다.<br/>건강진단서 번역, 입력받은 사용자의 건강정보를 기반으로 한 질병 예측, 그리고 AI 챗봇 상담 기능을 통해 보다 편리하고 효율적인 건강 관리를 지원합니다.",
  },
  {
    question: "제공하는 질병 예측은 어떤 정보를 기반으로 하나요?",
    answer:
      "질병 예측 서비스는 사용자가 입력한 개인 건강 정보(예: 연령, 성별, 건강 상태)를 기반으로 합니다.<br/> Python의 pandas 라이브러리를 사용하여 사용자의 건강 데이터를 로지스틱 회위방식으로  분석하고, 잠재적인 건강 위험을 예측합니다.<br/> 이 서비스는 예방적 조치를 취할 수 있도록 도와드립니다.",
  },

  {
    question:
      "웹사이트 이용 중 기술적 문제를 경험했을 때는 어떻게 해야 하나요?",
    answer:
      "기술적 어려움을 겪으셨다면, MediChart 고객센터-1:1문의를 이용해주세요. 문제를 파악한 후 신속하게 답변드리겠습니다.",
  },
];

const FAQ = () => {
  const [activeIndex, setActiveIndex] = useState(null);

  const handleToggle = (index) => {
    if (activeIndex === index) {
      setActiveIndex(null);
    } else {
      setActiveIndex(index);
    }
  };

  return (
    <section className="faq">
      {questions.map((item, index) => (
        <div className="faq-item" key={index}>
          <button className="faq-question" onClick={() => handleToggle(index)}>
            <div className="question-content">
              <FontAwesomeIcon icon={faQ} />
              <span className="question-text">{item.question}</span>
            </div>
            <span
              className={`toggle-icon ${activeIndex === index ? "open" : ""}`}
            >
              &#9660;
            </span>
          </button>

          {activeIndex === index && (
            <div className="faq-answer">
              <span className="answer-prefix">
                {" "}
                <FontAwesomeIcon icon={faA} style={{ color: "#56AEFF" }} />{" "}
              </span>
              <span dangerouslySetInnerHTML={{ __html: item.answer }} />
            </div>
          )}
        </div>
      ))}
    </section>
  );
};

export default FAQ;
