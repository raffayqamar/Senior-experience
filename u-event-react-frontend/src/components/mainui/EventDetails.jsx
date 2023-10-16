import React from "react";
import "../../css/EventDetails.css";
import { AvatarImage } from "../commonElements";

const EventDetails = ({
  eventName,
  user: { firstName, lastName },
  eventDate,
  location,
  description,
  image,
  tags,
}) => {
  const userName = `${firstName} ${lastName}`;
  const date = `${new Date(eventDate).toLocaleDateString("en-US", {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric",
  })}\n${new Date(eventDate).toLocaleTimeString("en-US", {
    hour: "numeric",
    minute: "numeric",
    hour12: true,
  })}`;
  return (
    <section className="event-detail">
      <div className="event-card-container">
        {/* EVENT CARD*/}
        <article
          className="event-card-content"
          style={{
            backgroundImage: `linear-gradient(
              rgba(0,0,0,1), 
              rgba(0,0,0,.6), 
              rgba(0,0,0,1)), 
              url(${image})`,
            backgroundRepeat: "no-repeat",
            backgroundPosition: "center center",
            backgroundSize: "cover",
            width: "100%",
            height: "100%",
            paddingBottom: "3rem",
            position: "relative",
          }}
        >
          <div className="tag-section">
            <p className="match-tag">Tags</p>
            <div className="event-tag">
              {tags.map((tag, index) => {
                return (
                  <p className="event-tag" key={index}>
                    {tag}
                  </p>
                );
              })}
            </div>
          </div>
          <img
            src={AvatarImage}
            alt="avatarImage"
            className="event-details-avatar"
          />
          <p className="attending-tag match-tag">Attending ({"80"})</p>
          <div className="card-content">
            <h2 className="card-title">{eventName}</h2>
            <p>Host: {userName}</p>
            <p>Meeting Time: {date}</p>
            <p>Location: {location}</p>
            {/* <p>Duration: {"event.duration"} hours</p> */}
            <div className="contact-block">
              <p className="event-details">
                <span className="event-details-header">Event Details:</span>{" "}
                <br /> <span className="event-details-text">{description}</span>
              </p>
              <button className="contact-btn">Match</button>
              <div className="contact-details">
                <p>User: {userName}</p>
                <p>Memeber Since: {2023}</p>
              </div>
            </div>
          </div>
        </article>
      </div>
      <p className="event-prompt-header-1">
        Current Viewing: <span className="event-name-bold">{eventName}</span> by
        Event <span className="event-name-bold">{userName}</span>
      </p>
      <p className="event-prompt-header-2">
        Are You Sure You Want To Attend This Event?
      </p>
      {/* Create back button navigate back to the event board */}
      <div className="btn-container">
        <br />
        <button className="btn join-event-btn">Join</button>
      </div>
    </section>
  );
};

export default EventDetails;
