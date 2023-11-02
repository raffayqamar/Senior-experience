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
  const [filter, setFilter] = useState("all");
  const [filteredData, setFilteredData] = useState([]);

  // TODO-Complete: Fetch events from the backend
  useEffect(() => {
    // fetch data from the backend
    fetch("http://localhost:8080/api/events")
      .then((res) => res.json())
      .then((data) => {
        setData(data);
      });
  }, []);

  useEffect(() => {
    setFilteredData(filterData(filter));
  }, [filter, data]);

  const handleEventClick = (event) => {
    setSelectedEvent(event);
  };

  const backBtnClicked = () => {
    setBackBtn(!backBtn);
  };

  const handleFilterChange = (e) => {
    const filter = e.target.value;
    setFilter(filter);
  };

  const filterData = (filter) => {
    const filteredData = data.filter((event) => {
      return event.category === filter;
    });
    return filteredData;
  };

  return (
    <section className="event-board">
      <p className="welcome-text">{`Welcome back ${
        // data[0] ? data[0].user.firstName : "User"
        localStorage.getItem("firstName")
          ? localStorage.getItem("firstName")
          : "User"
      }!`}</p>
      <img src={rectImageOne} className="bg-rec-image" alt="rectimage" />
      <img src={feedIcon} className="feed-icon" alt="feedicon" />
      <div className="event-board-top">
        <div className="event-board-header">
          <h1 className="event-board-title">Event Board</h1>
          <Link to="/event-map" className="event-board-map">
            View Map
          </Link>

          <div className="event-board-filter">
            {/* Filter Feature */}
            <p>Filter By</p>
            <select
              className="event-board-filter"
              onChange={handleFilterChange}
            >
              <option value="all">All</option>
              <option value="concert">Concert</option>
              <option value="sports">Sports</option>
              <option value="food">Food</option>
              <option value="outdoors">Outdoors</option>
              <option value="games">Games</option>
              <option value="other">Other</option>
            </select>
          </div>
        </div>
      </div>
      {selectedEvent ? (
        <div
          className="event-details-content"
          style={{
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
            {"\u2190 Event Board"}
          </button>
          <EventDetails {...selectedEvent} />
        </div>
      ) : (
        <div className="event-board-top">
          <div className="event-board-events">
            {filter === "all"
              ? data.map((event) => {
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
                })
              : filteredData.map((event) => {
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
