import React, { useState, useEffect } from "react";
import "../../css/EventCard.css";
import { AvatarImage } from "../commonElements";

const EventCard2Form = ({
  onSubmit,
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
  const [formData, setFormData] = useState({
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
    firstName,
    lastName,
    tags,
    backgroundImage,
  });

  useEffect(() => {
    setFormData({
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
      firstName,
      lastName,
      tags,
      backgroundImage,
    });
  }, [
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
    firstName,
    lastName,
    tags,
    backgroundImage,
  ]);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit} className="event-card-form">
      <article
        className="event-card"
        style={{
          width: formData.width + "px",
          height: formData.height + "px",
          fontSize: formData.fontSize + "px",
          backgroundImage: `linear-gradient(to top, rgba(0,0,0,1), rgba(0,0,0,.1)), url(${
            formData.backgroundImage ? formData.backgroundImage : formData.image
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
            fontSize: formData.fontSize - 25 + "px",
            animation: `contentRightInAnim 0.9s ease-in-out`,
          }}
        >
          <label>
            Event Name:
            <input
              type="text"
              name="eventName"
              value={formData.eventName}
              onChange={handleChange}
              className="card-title"
            />
          </label>
          <label>
            Host First Name:
            <input
              type="text"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
            />
          </label>
          <label>
            Host Last Name:
            <input
              type="text"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
            />
          </label>
          <label>
            Category:
            <input
              type="text"
              name="category"
              value={formData.category}
              onChange={handleChange}
              className="category"
            />
          </label>
          <label>
            Date:
            <input
              type="date"
              name="eventDate"
              value={formData.eventDate}
              onChange={handleChange}
            />
          </label>
          <label>
            Time:
            <input
              type="time"
              name="eventTime"
              value={formData.eventTime}
              onChange={handleChange}
            />
          </label>
          <label>
            Location:
            <input
              type="text"
              name="location"
              value={formData.location}
              onChange={handleChange}
            />
          </label>
          <label>
            City:
            <input
              type="text"
              name="city"
              value={formData.city}
              onChange={handleChange}
            />
          </label>
          <label>
            Postal Code:
            <input
              type="text"
              name="postalCode"
              value={formData.postalCode}
              onChange={handleChange}
            />
          </label>
          <label>
            Description:
            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
              className="event-details-text"
            />
          </label>
          <button type="submit">Update Event</button>
        </div>
      </article>
    </form>
  );
};

export default EventCard2Form;
