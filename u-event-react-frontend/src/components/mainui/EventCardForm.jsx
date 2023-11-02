import React, { useState, useEffect } from "react";

import { AvatarImage } from "../commonElements";

const EventCardForm = ({
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
    <form onSubmit={handleSubmit} className="manage-event-card-form">
      <article
        className="manage-event-card"
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
        <img
          src={AvatarImage}
          alt="avatarImage"
          className="manage-event-avatar"
        />
        <p className="manage-match-tag">Match</p>
        <div
          className="manage-card-content"
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
              className="manage-card-title manage-input"
            />
          </label>
          <label>
            Host First Name:
            <input
              type="text"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
              className="manage-card-firstname manage-input"
            />
          </label>
          <label>
            Host Last Name:
            <input
              type="text"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              className="manage-card-lastname manage-input"
            />
          </label>
          <label>
            Category:
            <input
              type="text"
              name="category"
              value={formData.category}
              onChange={handleChange}
              className="manage-card-category manage-input"
            />
          </label>
          <label>
            Date:
            <input
              type="date"
              name="eventDate"
              value={formData.eventDate}
              onChange={handleChange}
              className="manage-card-date manage-input"
            />
          </label>
          <label>
            Time:
            <input
              type="time"
              name="eventTime"
              value={formData.eventTime}
              onChange={handleChange}
              className="manage-card-event-time manage-input"
            />
          </label>
          <label>
            Location:
            <input
              type="text"
              name="location"
              value={formData.location}
              onChange={handleChange}
              className="manage-card-location manage-input"
            />
          </label>
          <label>
            City:
            <input
              type="text"
              name="city"
              value={formData.city}
              onChange={handleChange}
              className="manage-card-city manage-input"
            />
          </label>
          <label>
            Postal Code:
            <input
              type="text"
              name="postalCode"
              value={formData.postalCode}
              onChange={handleChange}
              className="manage-card-postalcode manage-input"
            />
          </label>
          <label>
            Description:
            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
              className="manage-event-details-text manage-input"
            />
          </label>
          <button type="submit">Update Event</button>
        </div>
      </article>
    </form>
  );
};

export default EventCardForm;
