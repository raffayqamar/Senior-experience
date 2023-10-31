import React from "react";
import "../../css/EventCard.css";
import { AvatarImage } from "../commonElements";

const EventCard = ({
  width,
  height,
  fontSize,
  eventName,
  eventDate,
  eventTime,
  location,
  city,
  postalCode,
  description,
  category,
  image,
  user: { firstName, lastName },
  tags,
  backgroundImage,
}) => {
  // console.log("backgroundImage: ", backgroundImage);

  const eventDateFormatted = eventDate
    ? new Date(eventDate).toLocaleDateString("en-US", {
        year: "numeric",
        month: "short",
        day: "numeric",
      })
    : null;

  return (
    <article
      className="event-card"
      style={{
        width: width + "px",
        height: height + "px",
        fontSize: fontSize + "px",
        backgroundImage: `linear-gradient(to top, rgba(0,0,0,1), rgba(0,0,0,.1)), url(${
          backgroundImage ? backgroundImage : image
        })`,
        backgroundRepeat: "no-repeat",
        backgroundPosition: "center center",
        backgroundSize: "cover",
      }}
    >
      <img src={AvatarImage} alt="avatarImage" className="event-avatar" />
      <p className="match-tag">Match</p>
      <div
        className="card-content"
        style={{
          fontSize: fontSize - 25 + "px",
          animation: `contentRightInAnim 0.9s ease-in-out`,
        }}
      >
        <h2 className="card-title">{eventName}</h2>
        <p>Host: {`${firstName} ${lastName}`}</p>
        <p
          className="category"
          style={{
            textTransform: "capitalize",
          }}
        >
          Category: {category}
        </p>
        <p>
          Date: {eventDateFormatted}
          {eventTime && ` | ${eventTime}`}
        </p>
        <p>
          Location: {`${location ? location + ", " : ""}${city} ${postalCode}`}
        </p>
        <p className="event-details">
          <span className="event-details-header">Event Details:</span> <br />{" "}
          <span className="event-details-text">{description}</span>
        </p>
        <div className="event-tags">
          {tags.map((tag, index) => (
            <p className="event-tag" key={index}>{`#${tag}`}</p>
          ))}
        </div>
        <div className="attend-block">
          <button className="attend-btn">Attend</button>
          <div className="contact-details">
            <p>User: {`${firstName} ${lastName}`}</p>
            <p>Member Since: {2023}</p>
          </div>
        </div>
      </div>
    </article>
  );
};

export default EventCard;
