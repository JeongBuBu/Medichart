import React, { useEffect, useState } from "react";
import axios from 'axios';
import "../pages/InquiryList.css";

const InquiryList = ({ username }) => {
    const [inquiries, setInquiries] = useState([]);

    useEffect(() => {
        const fetchInquiries = async () => {
            try {
                const response = await axios.get('/api/admin/inquiries', {
                    params: { username }
                });
                setInquiries(response.data);
            } catch (error) {
                console.error('Failed to fetch inquiries', error);
            }
        };

        fetchInquiries();
    }, [username]);

    return (
        <section className="inquiry-list">
            <h2>문의 내역</h2>
            {inquiries.length === 0 ? (
                <p>접수된 문의가 없습니다.</p>
            ) : (
                <table>
                    <thead>
                    <tr>
                        <th>번호</th>
                        <th>문의 제목</th>
                        <th>문의 내용</th>
                    </tr>
                    </thead>
                    <tbody>
                    {inquiries.map((inquiry, index) => (
                        <tr key={inquiry.id}>
                            <td>{index + 1}</td>
                            <td>{inquiry.title}</td>
                            <td>{inquiry.content}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </section>
    );
};

export default InquiryList;
