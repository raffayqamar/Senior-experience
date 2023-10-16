import React, { useState } from "react";
import "../css/Sidebar.css";
import { Link } from "react-router-dom";
import SidebarAccountStatus from "./SidebarAccountStatus";
import heartIcon from "../assets/heart-icon.svg";
import calendarIcon from "../assets/calendar-icon.svg";
import dashboardIcon from "../assets/dashboard-icon.svg";
import messagesIcon from "../assets/messages-icon.svg";
import plusIcon from "../assets/plus-icon.svg";

// const interestTestDb = [
//   {
//     id: 1,
//     interest: "Rock Climbing",
//   },
//   {
//     id: 2,
//     interest: "Dancing",
//   },
//   {
//     id: 3,
//     interest: "Cooking",
//   },
//   {
//     id: 4,
//     interest: "Sports",
//   },
//   {
//     id: 5,
//     interest: "Hiking",
//   },
// ];

const Sidebar = () => {
  const [hideSidebar, setHideSidebar] = useState(false);
  const [logo, setLogo] = useState("U-Event");
  const handleEventClick = () => {
    // Set Hide Sidebar To Opposite Of Current State
    setHideSidebar(!hideSidebar);
  };
  return (
    <div className={`sidebar-wrapper ${hideSidebar ? "show" : "hide"}`}>
      <div className="sidebar-component-fill"></div>
      <div className="sidebar-component-wrapper">
        <div className={`sidebar ${hideSidebar ? "show" : "hide"}`}>
          <div
            className={`hide-sidebar-btn ${hideSidebar ? "show" : "hide"}`}
            onClick={() => {
              handleEventClick();
            }}
          >
            {!hideSidebar ? (
              <p className="hide-sidebar-text">{"\u2192"}</p>
            ) : (
              <p className="hide-sidebar-text">{"\u2190"}</p>
            )}
          </div>
          <h1 className={`sidebar-logo`}>{hideSidebar ? "U-Event" : "UE"}</h1>

          <p className="sidebar-sub-text">Interests</p>
          {/* 
          <div className="sidebar-interests">
            {interestTestDb.map((int) => (
              <span className="pill interest" key={int.id}>
                {int.interest}
              </span>
            ))}
          </div> */}
          <div className="hosted-section">
            <p className="sidebar-sub-text">Create Event</p>
            {/* Link To Create Events */}
            <Link to="/create-events">
              <div className="create-event">
                <img
                  src={plusIcon}
                  className="create-event-btn"
                  alt="plus icon"
                ></img>
                <p className="create-event-text">Create</p>
              </div>
            </Link>
          </div>
          <div className="nav-section">
            <p className="sidebar-sub-text">Navigation Menu</p>
            <Link to="/" className="pill nav">
              <div className="sidebar-icon-container">
                <img
                  className="sidebar-icons"
                  src={dashboardIcon}
                  alt="dashboard icon"
                  height={25}
                />
                <p className="link-text">Dashboard</p>
              </div>
            </Link>
            <Link to="/" className="pill nav">
              <div className="sidebar-icon-container">
                <img
                  className="sidebar-icons"
                  src={messagesIcon}
                  alt="messages icon"
                  height={20}
                />
                <p className="link-text">Messages</p>
              </div>
            </Link>
            <Link to="/" className="pill nav">
              <div className="sidebar-icon-container">
                {/* Icon */}
                <img
                  className="sidebar-icons"
                  src={calendarIcon}
                  alt="calendar icon"
                  height={25}
                />
                <p className="link-text">Planner</p>
              </div>
            </Link>
          </div>
          <Link to="/account" className="pill settings">
            <SidebarAccountStatus />
          </Link>
        </div>
      </div>
    </div>
  );
};

export default Sidebar;
