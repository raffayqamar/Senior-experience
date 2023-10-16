import React, { useState } from "react";
import RegistrationOverlay2 from "./RegisterOverlay2";

/* Overlay to allow user to select method of registration */

const RegistrationOverlay1 = ({ onClose, onSelectMethod }) => {
  const [notification, setNotification] = useState(false);
  const [notificationText, setNotificationText] = useState("");

  /************************************************* */

  const [createForm, setCreateForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    passwordConfirm: "",
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

  // Create state to track whether user wants to subscribe to emails

  const [subscribeToEmails, setSubscribeToEmails] = useState(false);
  const handleToggleEmailSubscription = () => {
    setSubscribeToEmails(!subscribeToEmails);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Create a FormData object
    const formData = new FormData();

    // Create an object of the form fields
    const { firstName, lastName, email, password } = createForm;

    // Update the formData object
    formData.append("firstName", firstName);
    formData.append("lastName", lastName);
    formData.append("email", email);
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
          // Reset the form or navigate to a different page
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

  return (
    <div className="overlay1">
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
      <div className="overlay1-container">
        <form
          className="overlay2-form"
          onSubmit={(e) => {
            handleSubmit(e);
          }}
        >
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
              onChange={handleChange}
            />
            <label htmlFor="lastName"> Last Name </label>
            <input
              type="text"
              id="lastName"
              name="lastName"
              placeholder="Last Name"
              required
              onChange={handleChange}
            />
            <label htmlFor="email"> E-mail </label>
            <input
              type="text"
              id="email"
              name="email"
              placeholder="E-mail"
              required
              onChange={handleChange}
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
      </div>
    </div>
  );
};

{
  /* <button
  className="overlay1-button"
  onClick={(e) => {
    // onSelectMethod("email");
    e.preventDefault();
    openOverlay();
  }}
>
  {" "}
  Continue with Email{" "}
</button>; */
}

export default RegistrationOverlay1;
