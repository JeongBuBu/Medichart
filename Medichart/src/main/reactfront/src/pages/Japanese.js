import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCloudArrowUp, faTrashAlt } from "@fortawesome/free-solid-svg-icons";
import axios from "axios";
import "../pages/korean.css";

function Japanese() {
  const [dragging, setDragging] = useState(false);
  const [file, setFile] = useState(null);
  const [preview, setPreview] = useState("");
  const [originalText, setOriginalText] = useState("");
  const [translatedText, setTranslatedText] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (file) {
      const objectUrl = URL.createObjectURL(file);
      setPreview(objectUrl);
      setLoading(true);

      const formData = new FormData();
      formData.append("image", file);

      axios
          .post("/api/upload?language=ja", formData, {
            headers: {
              "Content-Type": "multipart/form-data",
            },
          })
          .then((response) => {
            setLoading(false); // 요청이 완료되면 로딩 상태를 false로 설정
            console.log("Response: ", response);
            if (response.data.uploadedText && response.data.translatedText) {
              setOriginalText(response.data.uploadedText);
              setTranslatedText(response.data.translatedText);
            } else {
              console.error("Unexpected response format:", response.data);
            }
          })
          .catch((error) => {
            setLoading(false); // 에러가 발생해도 로딩 상태를 false로 설정
            if (error.response) {
              console.error("Error response:", error.response.data);
            } else if (error.request) {
              console.error("Error request:", error.request);
            } else {
              console.error("Error message:", error.message);
            }
          });

      return () => URL.revokeObjectURL(objectUrl);
    }
  }, [file]);


  const handleDragEnter = (e) => {
    e.preventDefault();
    e.stopPropagation();
    setDragging(true);
  };

  const handleDragLeave = (e) => {
    e.preventDefault();
    e.stopPropagation();
    setDragging(false);
  };

  const handleDragOver = (e) => {
    e.preventDefault();
    e.stopPropagation();
  };

  const handleDrop = (e) => {
    e.preventDefault();
    e.stopPropagation();
    setDragging(false);

    const files = e.dataTransfer.files;
    if (files && files.length > 0) {
      setFile(files[0]);
    }
  };

  const handleFileChange = (e) => {
    const files = e.target.files;
    if (files && files.length > 0) {
      setFile(files[0]);
    }
  };

  const handleRemoveFile = () => {
    setFile(null);
    setPreview("");
    setOriginalText("");
    setTranslatedText("");
  };

  return (
    <div className="container">
      <div className="inner">
        <div className="sectionLeft">
          <h2>健康診断書の解釈</h2>
          <div id="line">
            <ul>
              <li>
                <Link to="/Korean" className="link">
                  -한국어
                </Link>
              </li>
              <li>
                <Link to="/Japanese" className="link">
                  -日本語
                </Link>
              </li>
              <li>
                <Link to="/Chinese" className="link">
                  -汉文
                </Link>
              </li>
            </ul>
          </div>
        </div>
        <div className="contents">
          <div className="imgBox">
            <div
              className={`forUser ${dragging ? "dragging" : ""}`}
              onDragEnter={handleDragEnter}
              onDragLeave={handleDragLeave}
              onDragOver={handleDragOver}
              onDrop={handleDrop}
            >
              {!file && (
                <label>
                  <input
                    type="file"
                    style={{ display: "none" }}
                    onChange={handleFileChange}
                  />
                  <FontAwesomeIcon icon={faCloudArrowUp} className="upload" />
                  <p>
                    ファイルをドラッグ＆ドロップするか、クリックしてファイルを選択します。
                  </p>
                </label>
              )}
            </div>
            {file && (
              <div className="file-info">
                {file.type.startsWith("image/") && (
                  <div className="preview-container">
                    <img
                      src={preview}
                      alt="Preview"
                      className="preview-image"
                    />
                    <button
                      onClick={handleRemoveFile}
                      className="remove-button"
                    >
                      <FontAwesomeIcon icon={faTrashAlt} />
                    </button>
                  </div>
                )}
                <div className="finalText">
                  {loading && <p>翻訳中です。 少々お待ちください···</p>}
                  {!loading && originalText && (
                    <div className="ocr-text">
                      <h3>元のテキスト:</h3>
                      <p>{originalText}</p>
                    </div>
                  )}
                  {!loading && translatedText && (
                    <div className="translated-text">
                      <h3>翻訳されたテキスト:</h3>
                      <p>{translatedText}</p>
                    </div>
                  )}
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Japanese;
