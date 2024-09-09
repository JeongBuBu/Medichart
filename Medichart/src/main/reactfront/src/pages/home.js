import styles from "./home.module.css";
import { Link } from "react-router-dom";

function Home() {
  return (
    <section className={styles.featureContainer}>
      <Link to="/Korean" className={styles.mainLink}>
        <div className={styles.featureItem}>
          <h2>건강진단서 해석</h2>
          <p>
            - 익숙하지 않은 의학용어를 쉽게 해석해 사용자들의 편의성을
            높입니다.(한국어/일본어/중국어 지원)
          </p>
        </div>
      </Link>
      <Link to="/Prediction" className={styles.mainLink}>
        <div className={styles.featureItem}>
          <h2>질병 예측 </h2>
          <p>
            - 사용자가 입력한 건강 정보를 기반으로 개인 맞춤형 질병 예측을
            제공합니다.
          </p>
          <p>
            - 잠재적인 건강 위험을 미리 파악하고, 예방 조치에 대한 유익한 정보를
            제공합니다.
          </p>
        </div>
      </Link>

      <div className={styles.featureItem}>
        <h2>AI 챗봇 상담</h2>
        <p>
          - 언제 어디서나 건강 관련 질문에 대해 실시간으로 답변을 받을 수
          있습니다.
        </p>
        <p>- AI 챗봇은 최신 의료 지식을 바탕으로 유용한 답변을 제공합니다.</p>
      </div>
    </section>
  );
}
export default Home;
