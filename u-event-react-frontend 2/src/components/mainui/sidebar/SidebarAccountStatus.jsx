import React, { useEffect, useState } from "react";
import "..//../../css/SidebarAccountStatus.css";

const SidebarAccountStatus = () => {
  const [imageBase64, setImageBase64] = useState(null);
  const username = localStorage.getItem("username");

  // TODO-Complete: Combined endpoint to fetch user data and image from the backend
  useEffect(() => {
    // Fetch user data and image from the backend
    fetch(`http://localhost:8080/images/image/${username}`)
      .then((res) => res.json())
      .then((userData) => {
        const imageId = userData[0]; // Assuming userData is an array
        return fetch(`http://localhost:8080/images/${imageId}`);
      })
      .then((res) => res.json())
      .then((data) => {
        const base64String = data.image;
        setImageBase64(base64String);
      })
      .catch((error) => {
        console.error("Error fetching image:", error);
      });
  }, [username]);

  return (
    <div className="sidebar-account-status">
      <div className="sidebar-account-status-container">
        {imageBase64 ? (
          <img
            src={`data:image/jpeg;base64,${imageBase64}`}
            className="avatar"
            width={100}
            height={100}
            alt="account_image"
          />
        ) : (
          <img
            src="https://via.placeholder.com/150"
            className="avatar"
            width={100}
            height={100}
            alt="placeholder_image"
          />
        )}
        <p className="sidebar-account-status-name">Account</p>
      </div>
    </div>
  );
};

export default SidebarAccountStatus;
