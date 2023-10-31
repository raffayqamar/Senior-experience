import React from "react";
import MainUI from "../src/components/mainui/MainUI";
import Sidebar from "./components/mainui/sidebar/Sidebar";

import Account from "./components/Account/Account";
import EventDetails from "./components/mainui/EventDetails";
import CreateEvent from "./components/mainui/CreateEvent";
import Login from "./components/login/Login";
import "./css/Login.css";
import "./css/Account.css";

import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import RegistrationOverlay2 from "./components/login/RegisterOverlay2";

//Upload Images
import ImageUpload from "./components/image/ImageUpload";
import ImageDisplay from "./components/image/ImageDisplay";
import ManageEvents from "./components/mainui/ManageEvents";

function App() {
  return (
    <>
      <Router>
        {/* <ImageUpload />
        <ImageDisplay /> */}
        <div className="divide-wall"></div>
        <div className="App">
          <Routes>
            <Route path="/login" element={<Login />} />
          </Routes>
          <Sidebar />
          <Routes>
            <Route path="/" element={<MainUI />} />
            <Route path="/account" element={<Account />} />
            <Route path="/login/register" element={<Login />} />
            <Route path="/event-details" element={<EventDetails />} />
            <Route path="/create-events" element={<CreateEvent />} />
            <Route path="/manage-events" element={<ManageEvents />} />
          </Routes>
        </div>
      </Router>
      <p className="component-heading">
        <p className="component-text-1">U-</p>{" "}
        <p className="component-text-2">Event</p>
      </p>
    </>
  );
}

export default App;
