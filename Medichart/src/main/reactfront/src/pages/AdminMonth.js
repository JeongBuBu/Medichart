import React, { useState, useEffect } from "react";
import styles from "./mymedicheck.module.css";
import { Link } from "react-router-dom";
import axios from "axios";
import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
} from "recharts";

const AdminMonth = () => {
  const [data, setData] = useState([]);

  // 월별 방문자 수 데이터 가져오기
  const getMonthlySignupData = async () => {
    try {
      const response = await axios.get("http://localhost:8080/admin/monthly-visitor-count");
      const rawData = response.data;

      // 날짜 생성 함수
      const generateDateLabels = (monthsAgo) => {
        const date = new Date();
        date.setMonth(date.getMonth() - monthsAgo);
        const year = date.getFullYear();
        const month = date.getMonth() + 1; // 0-based month index
        return `${year}년 ${month}월`; // '2024년 7월', '2024년 6월', etc.
      };

      // 데이터 변환
      return rawData.map((value, index) => ({
        name: generateDateLabels(index),
        방문자: value,
      }));
    } catch (error) {
      console.error("Error fetching monthly visitor data:", error);
      return [];
    }
  };

  // 월별 문의사항 데이터 가져오기
  const getMonthlyInquiryData = async () => {
    try {
      const response = await axios.get("http://localhost:8080/admin/monthly-inquiry-count");
      const rawData = response.data;

      // 날짜 생성 함수
      const generateDateLabels = (monthsAgo) => {
        const date = new Date();
        date.setMonth(date.getMonth() - monthsAgo);
        const year = date.getFullYear();
        const month = date.getMonth() + 1; // 0-based month index
        return `${year}년 ${month}월`; // '2024년 7월', '2024년 6월', etc.
      };

      // 데이터 변환
      return rawData.map((value, index) => ({
        name: generateDateLabels(index),
        문의사항: value,
      }));
    } catch (error) {
      console.error("Error fetching monthly inquiry data:", error);
      return [];
    }
  };

  // 데이터 병합 함수
  const mergeData = (signupData, inquiryData) => {
    const mergedData = {};

    // 방문자 수 데이터 병합
    signupData.forEach((item) => {
      mergedData[item.name] = { 방문자: item.방문자 };
    });

    // 문의사항 데이터 병합
    inquiryData.forEach((item) => {
      if (mergedData[item.name]) {
        mergedData[item.name] = { ...mergedData[item.name], 문의사항: item.문의사항 };
      } else {
        mergedData[item.name] = { 문의사항: item.문의사항 };
      }
    });

    // 병합된 데이터를 리스트로 변환
    return Object.keys(mergedData).map((key) => ({
      name: key,
      ...mergedData[key],
    }));
  };

  useEffect(() => {
    const fetchData = async () => {
      const [signupData, inquiryData] = await Promise.all([
        getMonthlySignupData(),
        getMonthlyInquiryData(),
      ]);

      const mergedData = mergeData(signupData, inquiryData);
      setData(mergedData);
      console.log(mergedData); // 데이터가 올바르게 설정되었는지 확인
    };

    fetchData();
  }, []);

  return (
      <div className={styles.container}>
        <div className={styles.sectionLeft}>
          <h2 className={styles.side}>관리자 페이지</h2>
          <div id="line">
            <ul>
              <li>
                <Link to="/admin/noticeList" style={{ textDecoration: "none" }} className="link">
                  - 공지사항 관리
                </Link>
              </li>
              <li>
                <Link to="/admin/inquiryList" style={{ textDecoration: "none" }} className="link">
                  - 문의사항 관리
                </Link>
              </li>
            </ul>
          </div>
        </div>
        <div style={{ flex: 1, marginLeft: "1px" }}>
          <div className="chart-container" style={{ marginTop: "140px" }}>
            <div style={{ textAlign: "right", marginBottom: "10px" }}>
              <Link to="/admin/main" className={styles.button}>
                오늘/
              </Link>
              <Link to="/admin/month" className={styles.button}>
                월별/
              </Link>
              <Link to="/admin/year" className={styles.button}>
                연도별
              </Link>
            </div>
            <ResponsiveContainer width="80%" height={500}>
              <LineChart
                  data={data}
                  margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
              >
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Line type="monotone" dataKey="방문자" stroke="#8884d8" />
                <Line type="monotone" dataKey="문의사항" stroke="#82ca9d" />
              </LineChart>
            </ResponsiveContainer>
          </div>
        </div>
      </div>
  );
};

export default AdminMonth;
