import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import EventCard from "./EventCard";
import "../../css/EventBoard.css";
import "../../css/EventCard.css";
import "../../css/EventDetails.css";
import EventDetails from "./EventDetails";
import rectImageOne from "../../assets/right-rect-1.svg";
import feedIcon from "../../assets/feed-icon.svg";

const EventBoard = () => {
  const [selectedEvent, setSelectedEvent] = useState(null);
  const [backBtn, setBackBtn] = useState(false);

  const [data, setData] = useState([]);
  useEffect(() => {
    // fetch data from the backend
    fetch("http://localhost:8080/api/events")
      .then((res) => res.json())
      .then((data) => {
        setData(data);
      });
  }, []);

  const handleEventClick = (event) => {
    setSelectedEvent(event);
  };

  const backBtnClicked = () => {
    setBackBtn(!backBtn);
  };

  return (
    <section className="event-board">
      <img src={rectImageOne} className="bg-rec-image" alt="rectimage" />
      <img src={feedIcon} className="feed-icon" alt="feedicon" />
      <div className="event-board-top">
        <div className="event-board-header">
          <h1 className="event-board-title">Event Board</h1>
          <Link to="/event-map" className="event-board-map">
            View Map
          </Link>

          <div className="event-board-filter">
            <p>Filter By</p>
            <select className="event-board-filter">
              <option value="today">Meeting Time</option>
              <option value="week">Host</option>
              <option value="month">Location</option>
              <option value="year">Duration</option>
            </select>
          </div>
        </div>
      </div>
      {selectedEvent ? (
        <div
          className="event-details-content"
          style={{
            // backgroundImage: `linear-gradient(to top, rgba(0,0,0,1), rgba(0,0,0,.5)), url(${selectedEvent.image})`,
            backgroundRepeat: "no-repeat",
            backgroundPosition: "center center",
            backgroundSize: "cover",
            width: "100%",
            // height: "calc(100vh - 8vh)",
            height: "100%",
            paddingBottom: "3rem",
            position: "relative",
          }}
        >
          <button
            className="btn back-btn"
            onClick={() => {
              backBtnClicked(false);
              setSelectedEvent(null);
            }}
          >
            {/* This will allow the user to navigate back to the event board */}
            {"\u2190 Event Board"}
          </button>
          <EventDetails {...selectedEvent} />
        </div>
      ) : (
        // show the event list here when the event is not selected
        <div className="event-board-top">
          <div className="event-board-events">
            {data.map((event) => {
              return (
                <div
                  className="event-board-event"
                  key={event.eventId}
                  onClick={() => handleEventClick(event)}
                >
                  <div className="zoom-effect">
                    <EventCard {...event} height={600} />
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      )}
    </section>
  );
};

export default EventBoard;
