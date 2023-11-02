import React, { useState, useEffect } from "react";
import "..//../css/Notification.css";

const Notification = ({ resText, setShowNotification }) => {
  useEffect(() => {
    const notificationTimeout = setTimeout(() => {
      setShowNotification(false);
    }, 1000); // 1 second

    return () => {
      clearTimeout(notificationTimeout);
    };
  }, [resText, setShowNotification]);

  return (
    <div className={`notification`}>
      <p>{`${resText}`}</p>
    </div>
  );
};

export default Notification;
