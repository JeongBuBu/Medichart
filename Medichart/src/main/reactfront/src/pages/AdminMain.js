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

const AdminMain = () => {
  const [data, setData] = useState([]);

  // 방문자 수 데이터 가져오기
  const getWeeklySignupData = async () => {
    try {
      const response = await axios.get("http://localhost:8080/admin/weekly-visitor-count");
      const rawData = response.data;

      // 날짜 생성 함수
      const generateDateLabels = (daysAgo) => {
        const date = new Date();
        date.setDate(date.getDate() - daysAgo);
        return `${date.getMonth() + 1}/${date.getDate()}`;
      };

      // 데이터 변환
      return rawData.map((value, index) => ({
        name: generateDateLabels(index),
        방문자: value,
      }));
    } catch (error) {
      console.error("Error fetching weekly visitor data:", error);
      return [];
    }
  };

  // 문의사항 데이터 가져오기
  const getWeeklyInquiryData = async () => {
    try {
      const response = await axios.get("http://localhost:8080/admin/weekly-inquiry-count");
      const rawData = response.data;

      // 날짜 생성 함수
      const generateDateLabels = (daysAgo) => {
        const date = new Date();
        date.setDate(date.getDate() - daysAgo);
        return `${date.getMonth() + 1}/${date.getDate()}`;
      };

      // 데이터 변환
      return rawData.map((value, index) => ({
        name: generateDateLabels(index),
        문의사항: value,
      }));
    } catch (error) {
      console.error("Error fetching weekly inquiry data:", error);
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
        getWeeklySignupData(),
        getWeeklyInquiryData(),
      ]);

      const mergedData = mergeData(signupData, inquiryData);
      setData(mergedData);
      console.log(mergedData);
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
        <div style={{ flex: 1, marginLeft: "10px" }}>
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
            <ResponsiveContainer width="80%" height={600}>
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

export default AdminMain;
