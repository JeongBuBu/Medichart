import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../pages/Notice.css';

const Notice = () => {
  const [notices, setNotices] = useState([]);
  const [activeIndex, setActiveIndex] = useState(null);

  useEffect(() => {
    fetchNotices();
  }, []);

  const fetchNotices = async () => {
    try {
      const response = await axios.get('/api/admin/notice'); // URL 수정
      if (response.data && response.data.content) {
        setNotices(response.data.content);
      } else {
        console.error('Unexpected response structure', response.data);
      }
    } catch (error) {
      console.error('Failed to fetch notices', error);
    }
  };


  const handleToggle = (index) => {
    setActiveIndex(activeIndex === index ? null : index);
  };

  return (
      <section className="notice">
        <h2>공지사항</h2>
        <table className="notice-table">
          <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>날짜</th>
          </tr>
          </thead>
          <tbody>
          {notices.map((item, index) => (
              <React.Fragment key={item.id}>
                <tr onClick={() => handleToggle(index)} className="notice-header">
                  <td>{index + 1}</td>
                  <td className="notice-title">{item.title}</td>
                  <td className="notice-date">{new Date(item.createdDate).toLocaleDateString()}</td>
                </tr>
                {activeIndex === index && (
                    <tr>
                      <td colSpan="3" className="notice-content">
                        {item.content}
                      </td>
                    </tr>
                )}
              </React.Fragment>
          ))}
          </tbody>
        </table>
      </section>
  );
};

export default Notice;