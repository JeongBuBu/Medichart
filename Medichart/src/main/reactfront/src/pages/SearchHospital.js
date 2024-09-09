import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { Map, MapMarker } from "react-kakao-maps-sdk";
import Modal from "react-modal";
import "../pages/korean.css";
import styles from "./mymedicheck.module.css";
import "../pages/SearchHospital.css";

// 모달 스타일 설정
const customStyles = {
  content: {
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
    width: "60%",
    height: "80%",
    padding: "0",
  },
  overlay: {
    zIndex: 1000,
  },
};

Modal.setAppElement("#root");

function SearchHospital() {
  const [userLocation, setUserLocation] = useState(null);
  const [places, setPlaces] = useState([]);
  const [mapCenter, setMapCenter] = useState(null);
  const [modalIsOpen, setIsOpen] = useState(false);
  const [selectedPlaceUrl, setSelectedPlaceUrl] = useState("");

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        const userLoc = {
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        };
        setUserLocation(userLoc);
        setMapCenter(userLoc);
      });
    }
  }, []);

  useEffect(() => {
    if (!userLocation) return;

    const { kakao } = window;
    const ps = new kakao.maps.services.Places();
    const callback = (result, status) => {
      if (status === kakao.maps.services.Status.OK) {
        const sortedPlaces = result.sort((a, b) => {
          const distanceA = getDistance(userLocation, {
            lat: parseFloat(a.y),
            lng: parseFloat(a.x),
          });
          const distanceB = getDistance(userLocation, {
            lat: parseFloat(b.y),
            lng: parseFloat(b.x),
          });
          return distanceA - distanceB;
        });
        setPlaces(sortedPlaces);
      }
    };
    ps.keywordSearch("건강검진 병원", callback, {
      location: new kakao.maps.LatLng(userLocation.lat, userLocation.lng),
      radius: 5000,
    });
  }, [userLocation]);

  const getDistance = (loc1, loc2) => {
    const R = 6371;
    const dLat = deg2rad(loc2.lat - loc1.lat);
    const dLng = deg2rad(loc2.lng - loc1.lng);
    const a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(deg2rad(loc1.lat)) *
        Math.cos(deg2rad(loc2.lat)) *
        Math.sin(dLng / 2) *
        Math.sin(dLng / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const distance = R * c * 1000; // meters
    return distance;
  };

  const deg2rad = (deg) => {
    return deg * (Math.PI / 180);
  };

  const openModal = (url) => {
    setSelectedPlaceUrl(url);
    setIsOpen(true);
  };

  const closeModal = () => {
    setIsOpen(false);
    setSelectedPlaceUrl("");
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
                    className="link"
                >
                  - 건강검진정보
                </Link>
              </li>
              <li>
                <Link
                    to="/Prediction"
                    style={{ textDecoration: "none" }}
                    className="link"
                >
                  - 질병 예측
                </Link>
              </li>
              <li>
                <Link
                    to="/SearchHospital"
                    style={{ textDecoration: "none" }}
                    className="link"
                >
                  - 검진센터 찾기
                </Link>
              </li>
            </ul>
          </div>
        </div>
        <div className="outline">
          <h2 className={styles.title}>건강검진 센터 찾기</h2>
          {mapCenter && (
              <Map
                  center={{ lat: mapCenter.lat, lng: mapCenter.lng }}
                  className="Map"
                  level={3}
                  style={{ width: "100%", height: "400px" }}
              >
                {userLocation && (
                    <MapMarker
                        position={userLocation}
                        title="현재 위치"
                        image={{
                          src: "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png",
                          size: { width: 24, height: 35 },
                        }}
                    />
                )}
                {places.map((place, index) => (
                    <MapMarker
                        key={index}
                        position={{
                          lat: parseFloat(place.y),
                          lng: parseFloat(place.x),
                        }}
                        title={place.place_name}
                        onClick={() => openModal(place.place_url)}
                    />
                ))}
              </Map>
          )}
          <ul style={{ listStyleType: "none", padding: 0 }}>
            {places.map((place, index) => (
                <li key={index}>
                  <button
                      className="hospital_list"
                      onClick={() => openModal(place.place_url)}
                  >
                    {place.place_name}
                  </button>
                  ({Math.round(getDistance(userLocation, {
                  lat: parseFloat(place.y),
                  lng: parseFloat(place.x),
                }))} m)
                </li>
            ))}
          </ul>
          <Modal
              isOpen={modalIsOpen}
              onRequestClose={closeModal}
              style={customStyles}
              contentLabel="Hospital Detail"
          >
            <div style={{ position: "relative", height: "100%" }}>
              <iframe
                  src={selectedPlaceUrl}
                  style={{
                    width: "100%",
                    height: "calc(100% - 50px)",
                    border: "none",
                  }}
                  title="Hospital Detail"
              />
              <button
                  onClick={closeModal}
                  style={{
                    position: "absolute",
                    top: "10px",
                    right: "10px",
                    padding: "5px 10px",
                    border: "none",
                    backgroundColor: "#333",
                    color: "#fff",
                    cursor: "pointer",
                  }}
              >
                닫기
              </button>
            </div>
          </Modal>
        </div>
      </div>
  );
}

export default SearchHospital;
