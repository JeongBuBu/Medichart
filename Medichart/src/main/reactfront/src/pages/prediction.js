import { Link } from "react-router-dom";
import styles from "./prediction.module.css";
import React, {useState, useEffect, useContext} from "react";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from "recharts";
import Loading from "./Loading";
import Modal from "react-modal";
import {AuthContext} from "./AuthContext";


// 날짜 포맷 함수
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toLocaleString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' }).replace(',', '');
};

// yAxis Tick Formatter
const yAxisTickFormatter = (value) => {
  if (value === 25) return "주의";
  if (value === 50) return "경고";
  if (value === 75) return "위험";
  return "";
};

// 모달 스타일 정의
const modalStyles = {
  content: {
    top: '50%',
    left: '50%',
    right: 'auto',
    bottom: 'auto',
    marginRight: '-50%',
    transform: 'translate(-50%, -50%)',
    width: '80%',
    maxWidth: '600px',
    height: '70%',
    maxHeight: '500px',
    overflowY: 'auto',
  },
  overlay: {
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
};

const Prediction = () => {
  const [chartData, setChartData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showChart, setShowChart] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [pastResults, setPastResults] = useState([]);
  const [selectedDate, setSelectedDate] = useState(null);
  const { isLoggedIn, username } = useContext(AuthContext);
  console.log(username);
  useEffect(() => {
    if (showChart) {
      setLoading(true);
      const fetchData = async () => {
        try {
          const response = await fetch(`http://localhost:8080/api/predictions?username=${username}`);

          // Check if response status is OK
          if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
          }

          const result = await response.json();
          console.log("Fetched data:", result);

          setPastResults(result);
          setChartData([]);
        } catch (error) {
          console.error("Error fetching data: ", error);
        } finally {
          setLoading(false);
        }
      };
      fetchData();
    }
  }, [showChart, username]);

  const handleButtonClick = () => {
    setShowChart(true);
    setModalOpen(true); // 모달 열기
  };

  const handleModalClose = () => {
    setModalOpen(false);
  };

  const handleDateSelect = (date) => {
    const selectedResult = pastResults.find(result => result.createdAt === date);

    if (selectedResult) {
      const transformedData = [
        { name: "당뇨병", '예측 확률': selectedResult.diabetes },
        { name: "심장병", '예측 확률': selectedResult.heartDisease },
        { name: "고혈압", '예측 확률': selectedResult.hypertension },
        { name: "신장질환", '예측 확률': selectedResult.kidney },
        { name: "비만", '예측 확률': selectedResult.obesity },
        { name: "뇌졸중", '예측 확률': selectedResult.stroke }
      ];

      setChartData(transformedData);
      setSelectedDate(date);
      setModalOpen(false); // 날짜를 선택하면 모달 닫기
    }
  };

  return (
      <div className={styles.container}>
        <div className={styles.sectionLeft}>
          <h2 className={styles.side}>나의 메디체크</h2>
          <div id="line">
            <ul>
              <li>
                <Link
                    to="/MedicalInform"
                    style={{ textDecoration: "none" }}
                    className={styles.link}
                >
                  - 건강검진정보
                </Link>
              </li>
              <li>
                <Link
                    to="/Prediction"
                    style={{ textDecoration: "none" }}
                    className={styles.link}
                >
                  - 질병 예측
                </Link>
              </li>
              <li>
                <Link
                    to="/SearchHospital"
                    style={{ textDecoration: "none" }}
                    className={styles.link}
                >
                  - 검진센터 찾기
                </Link>
              </li>
            </ul>
          </div>
        </div>
        <div className={styles.inner}>
          <h2 className={styles.title}>질병 예측</h2>
          <p className={styles.content}>
            인공지능 건강 예측 프로그램은 예측결과와 주요인자 분석을 통해
            건강위험요인을 미리 확인하고 예방하기 위한 목적으로 개발되었습니다.
          </p>
          <p className={styles.content2}>
            ※질환에 대한 정확한 진단과 치료는 의료진과 상의하시기 바랍니다.
          </p>

          <button className={styles.pButton} onClick={handleButtonClick}>
            결과확인
          </button>
          {showChart && (
              loading ? (
                  <Loading />
              ) : (
                  <ResponsiveContainer width={900} height={500}>
                    <BarChart
                        width={500}
                        height={300}
                        data={chartData}
                        margin={{
                          top: 5,
                          right: 30,
                          left: 20,
                          bottom: 5,
                        }}
                        barSize={50}
                    >
                      <XAxis
                          dataKey="name"
                          scale="point"
                          padding={{ left: 70, right: 10 }}
                      />
                      <YAxis
                          domain={[0, 100]}
                          tickFormatter={yAxisTickFormatter}
                          ticks={[25, 50, 75]}
                      />
                      <Tooltip />
                      <Legend />
                      <CartesianGrid strokeDasharray="3 3" />
                      <Bar
                          dataKey="예측 확률"
                          fill="#8884d8"
                          background={{ fill: "#eee" }}
                      />
                    </BarChart>
                  </ResponsiveContainer>
              )
          )}
        </div>

        <Modal
            isOpen={modalOpen}
            onRequestClose={handleModalClose}
            style={modalStyles}
            contentLabel="Prediction Results"
        >
          <h2>과거 건강검진정보</h2>
          <table className={styles.table}>
            <thead>
            <tr>
              <th>번호</th>
              <th>검진일</th>
            </tr>
            </thead>
            <tbody>
            {pastResults.map((item, index) => (
                <tr key={index}>
                  <td>{index + 1}</td>
                  <td>
                    <button onClick={() => handleDateSelect(item.createdAt)}>
                      {formatDate(item.createdAt)}
                    </button>
                  </td>
                </tr>
            ))}
            </tbody>
          </table>
        </Modal>
      </div>
  );
};

export default Prediction;
