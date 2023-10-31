import React from "react";
import "../../css/EventDetails.css";
import { AvatarImage } from "../commonElements";

// TODO-Complete: Separate EventPrompt component
const EventPrompt = ({ eventName, userName }) => {
  return (
    <div>
      <p className="event-prompt-header-1">
        Current Viewing: <span className="event-name-bold">{eventName}</span> by
        Event <span className="event-name-bold">{userName}</span>
      </p>
      <p className="event-prompt-header-2">
        Are You Sure You Want To Attend This Event?
      </p>
    </div>
  );
};

// TODO-Complete: Reusable Button component
const Button = ({ onClick, label }) => {
  return (
    <button className="btn" onClick={onClick}>
      {label}
    </button>
  );
};

// TODO-Complete: Reusable Date Component
const DateComponent = ({ eventDate }) => {
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
  return <p>Meeting Time:{date}</p>;
};

// TODO-Complete: Tag Component
const TagComponent = ({ tags }) => {
  // Fragment used to avoid unnecessary divs in the DOM
  return (
    <>
      {tags.map((tag, index) => {
        return (
          <p className="event-tag-content" key={index}>
            {`#${tag}`}
          </p>
        );
      })}
    </>
  );
};

// TODO-Complete: Main EventDetails component + subcomponents
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

  return (
    <section className="event-detail">
      <div className="event-card-container">
        {/* EVENT CARD */}
        <article
          className="event-card-content"
          style={{
            backgroundImage: `linear-gradient(
              rgba(0,0,0,1), 
              rgba(0,0,0,.6), 
              rgba(0,0,0,1)), 
              url(${image})`,
          }}
        >
          <div className="tag-section">
            <p className="event-details-match-tag">Tags</p>
            <div className="event-tag-container">
              {/* Tag Component */}
              <TagComponent tags={tags} />
              {/* End of Tag Component */}
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
            {/* Date Component */}
            <DateComponent eventDate={eventDate} />
            {/* End of Date Component */}
            <p>Location: {location}</p>
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
        {/* End of EVENT CARD */}
      </div>
      {/* EventPrompt component */}
      <EventPrompt eventName={eventName} userName={userName} />
      {/* End of EventPrompt component */}
      <div className="btn-container">
        <br />
        {/* Use the reusable Button component */}
        <div className="btn-container">
          <Button
            label="Join"
            onClick={() => {
              /* TODO: Setup handle joining events!! */
            }}
          />
        </div>
      </div>
    </section>
  );
};

export default EventDetails;
