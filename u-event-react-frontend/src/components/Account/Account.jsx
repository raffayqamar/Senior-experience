import React, { useState } from "react";
import { useRef } from "react";

/** User Profile Card */

const userData = [
  {
    firstName: "John",
    lastName: "Doe",
    desc: "Software Developer",
    nickname: "JD",
    email: "jd@something.com",
    phone: "(123)-456-7890",
    address: "1234 Main St",
    password: "password",
    confirmPassword: "password",

    // Add other user data
  },
];

// const [phone, setPhone] = useState("1234567890");

const Account = () => {
  // Initial profile picture URL
  const [profilePicture, setProfilePicture] = useState(
    "https://via.placeholder.com/150"
  ); //placeholder image
  // Handle the file input

  const fileInputRef = useRef(null);

  const openFileInput = () => {
    fileInputRef.current.click();
  };

  const handleImageChange = async (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile) {
      try {
        // Create a FormData object to send the file to the server
        const formData = new FormData();
        formData.append("image", selectedFile);

        // Send a POST request to your server to upload the image using the fetch API
        const response = await fetch("/api/upload", {
          method: "POST",
          body: formData,
          // Make sure to set the 'Content-Type' header to 'multipart/form-data'
          headers: {
            // Remove the 'Content-Type' header from here since it's automatically set by the FormData object
          },
        });

        if (response.ok) {
          const data = await response.json();
          // Update the profilePicture state with the URL of the uploaded image
          setProfilePicture(data.imageUrl);
        } else {
          console.error("Error uploading image:", response.status);
        }
      } catch (error) {
        console.error("Error uploading image:", error);
      }
    }
  };

  /** User Details Card */
  // // to change each individual fiels when user makes a change on details card
  const [changeField, setChangeField] = useState(false);

  const handleChangeField = () => {
    setChangeField(!changeField);
  };

  //handle the submit button on the details card
  const handleSubmit = (e) => {
    e.preventDefault();
    // Add code to submit form data to backend
    // console.log(
    //   "Updated user details:" +
    //     firstName +
    //     lastName +
    //     jobDescription +
    //     nickname +
    //     email +
    //     phone +
    //     address +
    //     password +
    //     confirmPassword
    // );
    console.log("Updated user details:" + userData);
  };

  /** Interests Card */

  const [selectedInterests, setSelectedInterests] = useState([]);
  const interests = [
    "Archery",
    "Dancing",
    "Cooking",
    "Sports",
    "Hiking",
    "Painting",
    "Rock-Climbing",
  ]; // Example interests

  const [editDetails, setEditDetails] = useState(false);

  const toggleInterest = (interest) => {
    if (selectedInterests.includes(interest)) {
      setSelectedInterests(
        selectedInterests.filter((item) => item !== interest)
      );
    } else {
      setSelectedInterests([...selectedInterests, interest]);
    }
  };

  /** Events Card */

  const [events, setEvents] = useState([
    { id: 1, name: "Event 1", date: "2021-01-01" },
    { id: 2, name: "Event 2", date: "2021-01-02" },
    { id: 3, name: "Event 3", date: "2021-01-03" },
  ]);

  return (
    <div className="accounts-page">
      <div className="accounts-grid-block">
        <div className="grid-content">
          {/* User Profile Card Section */}

          {/* USER CONTAINER */}
          <div
            className={`user-profile-container ${editDetails ? "edit" : ""}`}
          >
            <div className="user-profile-card">
              <div className="user-profile-card-body">
                <div className="user-profile-card-body-content">
                  <div className="user-profile-card-body-content-image">
                    <img src={profilePicture} alt="user profile" />
                    {/* Add an input element for image upload */}
                    <input
                      type="file"
                      accept="image/*"
                      onChange={handleImageChange}
                      style={{ display: "none" }}
                      ref={fileInputRef}
                    />
                  </div>
                  <div className="user-profile-card-body-content-text">
                    {userData.map(
                      ({ firstName, lastName, desc, nickname, email }, idx) => {
                        return (
                          <div className="meta-data" key={idx}>
                            <h3>{`${firstName} ${lastName}`}</h3>
                            <p>{desc}</p>
                            <p>{nickname}</p>
                            <p>{email}</p>
                          </div>
                        );
                      }
                    )}
                  </div>
                  <button
                    className="add-profile-picture-button"
                    onClick={openFileInput}
                  >
                    Add/Change Profile Picture
                  </button>
                </div>
              </div>
            </div>

            {/* User Details Card Section */}

            <div
              className={`user-details-card
            ${editDetails ? "edit" : ""}`}
            >
              <div className="user-details-card-body">
                <div className="user-details-card-header">
                  <h2>Details</h2>
                </div>
                <div className="user-details-card-body-content">
                  <button
                    className="edit-user-details-button"
                    onClick={
                      editDetails
                        ? () => setEditDetails(false)
                        : () => setEditDetails(true)
                    }
                  >
                    Edit User Details
                  </button>
                  <form
                    onSubmit={handleSubmit}
                    className="accounts-form-container"
                  >
                    <div className="user-details-card-body-content-text">
                      {userData.map((user, idx) => {
                        const {
                          firstName,
                          lastName,
                          desc,
                          nickname,
                          email,
                          phone,
                          address,
                          password,
                          confirmPassword,
                        } = user;
                        return (
                          <div key={idx}>
                            <label htmlFor="firstName">First Name</label>
                            <input
                              type="text"
                              id="firstName"
                              name="firstName"
                              value={firstName}
                              onChange={handleChangeField}
                            />
                            <label htmlFor="lastName">Last Name</label>
                            <input
                              type="text"
                              id="lastName"
                              name="lastName"
                              value={lastName}
                              onChange={handleChangeField}
                            />
                            <label htmlFor="jobDescription">
                              Job Description
                            </label>
                            <input
                              type="text"
                              id="jobDescription"
                              name="jobDescription"
                              value={desc}
                              onChange={handleChangeField}
                            />
                            {/* console.log('Updated user details:' + name + jobDescription + nickname + email + phone + address + password + confirmPassword) */}
                            <label htmlFor="nickname">Nickname</label>
                            <input
                              type="text"
                              id="nickname"
                              name="nickname"
                              value={nickname}
                              onChange={handleChangeField}
                            />
                            <label htmlFor="email">Email</label>
                            <input
                              type="email"
                              id="email"
                              name="email"
                              value={email}
                              onChange={handleChangeField}
                            />
                            <label htmlFor="phone">Phone</label>
                            <input
                              type="tel"
                              id="phone"
                              name="phone"
                              value={phone}
                              onChange={handleChangeField}
                            />
                            <label htmlFor="address">Address</label>
                            <input
                              type="text"
                              id="address"
                              name="address"
                              value={address}
                              onChange={handleChangeField}
                            />
                            <label htmlFor="password">Password</label>
                            <input
                              type="password"
                              id="password"
                              name="password"
                              value={password}
                              onChange={handleChangeField}
                            />
                            <label htmlFor="confirmPassword">
                              Confirm Password
                            </label>
                            <input
                              type="password"
                              id="confirmPassword"
                              name="confirmPassword"
                              value={confirmPassword}
                              onChange={handleChangeField}
                            />
                          </div>
                        );
                      })}
                    </div>
                    <div className="user-details-card-body-content-button">
                      <button type="submit-updated-user-details">Update</button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
          {/* USER CONTAINER */}

          {/* <UserInterestsCard/> */}

          <div className="user-profile-lower-container">
            <div className="interests-card">
              <h2>Interests</h2>
              <div className="interests-selection-box">
                {interests.map((interest) => (
                  <div className="interest-wrapper" key={interest}>
                    <input
                      type="checkbox"
                      checked={selectedInterests.includes(interest)}
                      onChange={() => toggleInterest(interest)}
                    />
                    <span>{interest}</span>
                  </div>
                ))}
              </div>
            </div>

            {/* <UserEventsCard/> */}

            <div className="events-card">
              <h2>Events</h2>
              <div className="events-signup-listing">
                {events.map((event) => (
                  <p key={event.id}>
                    {event.name} - {event.date}
                  </p>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Account;
