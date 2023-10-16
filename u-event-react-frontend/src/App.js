import React from "react";
import MainUI from "./components/mainui/MainUI";
import Sidebar from "./components/Sidebar";

import Account from "./components/Account/Account";
import EventDetails from "./components/mainui/EventDetails";
import CreateEvent from "./components/mainui/CreateEvent";
import Login from "./components/login/Login";
import "./css/Account.css";

import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/login" element={<Login />} />
        </Routes>
        <Sidebar />
        <Routes>
          <Route path="/" element={<MainUI />} />
          <Route path="/account" element={<Account />} />
          <Route path="/event-details" element={<EventDetails />} />
          <Route path="/create-events" element={<CreateEvent />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
