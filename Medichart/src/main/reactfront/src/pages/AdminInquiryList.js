import React, { useState, useEffect } from 'react';
import axios from 'axios';
import styles from './mymedicheck.module.css';
import { Link } from 'react-router-dom';

const AdminInquiryList = () => {
    const [inquiries, setInquiries] = useState([]);
    const [search, setSearch] = useState('');
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        fetchInquiries();
    }, [search, page, size]);

    const fetchInquiries = async () => {
        try {
            const response = await axios.get('/admin/inquiries', { // 수정된 엔드포인트
                params: { page, size, search }
            });
            if (response.data && response.data.content) {
                setInquiries(response.data.content);
                setTotalPages(response.data.totalPages);
            } else {
                console.error('Unexpected response structure', response.data);
            }
        } catch (error) {
            console.error('Failed to fetch inquiries', error);
        }
    };

    const handleSearchChange = (e) => {
        setSearch(e.target.value);
    };

    const handleSearchSubmit = (e) => {
        e.preventDefault();
        setPage(0); // Reset to first page on search
        fetchInquiries();
    };

    const handleDelete = async (id) => {
        try {
            await axios.delete(`/api/admin/inquiries/${id}`); // 수정된 URL
            fetchInquiries(); // 삭제 후 목록 갱신
        } catch (error) {
            console.error('Failed to delete inquiry', error);
        }
    };

    const pageSize = size;
    const startIndex = page * pageSize;

    return (
        <div className={styles.container}>
            <div className={styles.sectionLeft}>
                <h2 className={styles.side}>문의사항 관리</h2>
                <div id="line">
                    <ul>
                        <li>
                            <Link to="/admin/inquiryList" style={{ textDecoration: 'none' }} className="link">
                                - 문의사항 목록
                            </Link>
                        </li>
                    </ul>
                </div>
            </div>
            <section className={styles.inner}>
                <div className={styles.titleTop}>
                    <h2 className={styles.title}>문의사항 목록</h2>
                    <form onSubmit={handleSearchSubmit} className={styles.searchForm}>
                        <input
                            type="text"
                            value={search}
                            onChange={handleSearchChange}
                            placeholder="검색어 입력"
                            className={styles.searchInput}
                        />
                        <button type="submit" className={styles.searchButton}>검색</button>
                    </form>
                </div>
                <table className={styles.noticeTable}>
                    <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>내용</th>
                        <th>작성 일자</th>
                        <th>작성자</th> {/* username 열 추가 */}
                        <th>액션</th> {/* 삭제 버튼을 위한 열 추가 */}
                    </tr>
                    </thead>
                    <tbody>
                    {inquiries.map((inquiry, index) => (
                        <tr key={inquiry.id}>
                            <td>{startIndex + index + 1}</td>
                            <td>{inquiry.title}</td>
                            <td>{inquiry.content}</td>
                            <td>{new Date(inquiry.createdAt).toLocaleString()}</td> {/* 날짜 형식 수정 */}
                            <td>{inquiry.username}</td> {/* username 표시 */}
                            <td>
                                <button onClick={() => handleDelete(inquiry.id)}>삭제</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                <div className={styles.pagination}>
                    {[...Array(totalPages).keys()].map((num) => (
                        <button
                            key={num}
                            onClick={() => setPage(num)}
                            disabled={num === page}
                        >
                            {num + 1}
                        </button>
                    ))}
                </div>
            </section>
        </div>
    );
};

export default AdminInquiryList;