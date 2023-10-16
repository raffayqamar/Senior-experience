import React from "react";
import "../css/SidebarAccountStatus.css";
import { AvatarImage } from "./commonElements";

const SidebarAccountStatus = () => {
  return (
    <div className="sidebar-account-status">
      <div className="sidebar-account-status-container">
        <img src={AvatarImage} alt="Avatar" className="avatar" />
        {/* </div> */}
        <p className="sidebar-account-status-name">Account</p>
      </div>
    </div>
  );
};

export default SidebarAccountStatus;
