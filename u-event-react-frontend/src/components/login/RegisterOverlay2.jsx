import React, { useState, useRef } from "react";
import { Link } from "react-router-dom";

const RegistrationOverlay2 = ({ onClose, onRegisterEmail }) => {
  const [notification, setNotification] = useState(false);
  const [notificationText, setNotificationText] = useState("");

  // For the redirect link after account creation
  const linkRef = useRef();

  /************************************************* */

  const [createForm, setCreateForm] = useState({
    firstName: "",
    lastName: "",
    username: "",
    password: "",
    passwordConfirm: "",
    role: "USER",
    provider: "LOCAL",
  });

  const showNotification = () => {
    if (formReadyToSubmit) {
      setNotification(true);
      setTimeout(() => {
        setNotification(false);
      }, 2000);
    }
  };

  // UPDATE FORM CONTENT FOR POST REQUEST
  const handleChange = (e) => {
    setCreateForm({
      ...createForm,
      [e.target.name]: e.target.value,
    });
    console.log(createForm);
  };

  const [formReadyToSubmit, setFormReadyToSubmit] = useState(false);

  // Create state to track whether overlay[Modal] is open or not
  const [isOverlayOpen, setIsOverlayOpen] = useState(false);
  // Arrow function to open the overlay
  const openOverlay = () => {
    setIsOverlayOpen(true);
  };
  // Arrow function to close the overlay
  const closeOverlay = () => {
    setIsOverlayOpen(false);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Create a FormData object
    const formData = new FormData();

    // Create an object of the form fields
    const { firstName, lastName, username, password } = createForm;

    // Update the formData object
    formData.append("firstName", firstName);
    formData.append("lastName", lastName);
    formData.append("username", username);
    formData.append("password", password);

    console.log(formData);

    // Make a POST request to your backend endpoint
    fetch("http://localhost:8080/api/users", {
      method: "POST",
      body: JSON.stringify(createForm),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (response.ok) {
          // The request was successful, you can handle the response here
          console.log("User created successfully");
          alert("Account Created");

          // Navigate to the close the overlay
          closeOverlay();
        } else {
          // The request failed, handle the error here
          console.error("Failed to create user");
          alert("Account NOT Created 😭");
          // Display an error message or handle the error as needed
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        // Handle any network errors here
      });
  };

  /************************************************* */
  // const handleSubmit = (event) => {
  //   event.preventDefault();
  //   console.log(email);
  // };

  return (
    <div className="overlay2">
      <div className="overlay2-content">
        <form
          className="overlay2-form"
          onSubmit={(e) => {
            handleSubmit(e);
          }}
        >
          <div className="create-close-btn-container">
            <button
              className="overlay-close-button"
              data-testid="close-overlay-button"
              onClick={onClose}
            >
              {/*
                    // The following line is a unicode character for the multiplication sign
                    */}
              {"\u00D7"}
            </button>
          </div>

          <div className="create-close-btn-container">
            {/* <button className="overlay-back-button"> back </button>
            <button className="overlay-close-button" onClick={onClose}>
              {" "}
              close{" "}
            </button> */}
          </div>
          <h2 className="overlay-2-header"> Create Account By Email </h2>
          <div className="overlay-input-container">
            <label htmlFor="firstName"> First Name </label>
            <input
              type="text"
              id="firstName"
              name="firstName"
              placeholder="First Name"
              required
              onChange={(e) => {
                handleChange(e);
                localStorage.setItem("firstName", e.target.value);
              }}
            />
            <label htmlFor="lastName"> Last Name </label>
            <input
              type="text"
              id="lastName"
              name="lastName"
              placeholder="Last Name"
              required
              onChange={(e) => {
                handleChange(e);
                localStorage.setItem("lastName", e.target.value);
              }}
            />
            <label htmlFor="email"> E-mail </label>
            <input
              type="text"
              id="email"
              name="username"
              placeholder="E-mail"
              required
              onChange={(e) => {
                handleChange(e);
                localStorage.setItem("username", e.target.value);
              }}
            />
            <label htmlFor="password"> Password </label>
            <input
              type="text"
              id="password"
              name="password"
              placeholder="Password"
              required
              onChange={handleChange}
            />
            <label htmlFor="passwordConfirm"> Confirm Password </label>
            <input
              type="text"
              id="passwordConfirm"
              name="passwordConfirm"
              placeholder="Confirm Password"
              required
              onChange={handleChange}
            />
            <button
              className="overlay2-button"
              type="submit"
              onClick={(e) => {
                handleSubmit(e);
                alert("Account Created");
              }}
            >
              {" "}
              Register{" "}
            </button>
          </div>
        </form>
        {/* Redirect Link After Account Creation */}
        <Link to="/account" ref={linkRef} style={{ display: "none" }}></Link>
        {/* End of Redirect Link After Account Creation */}
      </div>
    </div>
  );
};

export default RegistrationOverlay2;
