import React, { useState, useRef } from "react";
import { Link } from "react-router-dom";
// import RegistrationOverlay1 from "./RegisterOverlay1";
import RegistrationOverlay2 from "./RegisterOverlay2";
import "../../css/Login.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCoffee } from "@fortawesome/free-solid-svg-icons";
import {
  faApple,
  faFacebook,
  faGoogle,
} from "@fortawesome/free-brands-svg-icons";
import loginimage from "../../assets/ugur-arpaci-U18V0ToioFU-unsplash.jpg";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const linkRef = useRef();

  const [createForm, setCreateForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    passwordConfirm: "",
  });

  // UPDATE FORM CONTENT FOR GET REQUEST
  const handleChange = (e) => {
    setCreateForm({
      ...createForm,
      [e.target.name]: e.target.value,
    });
    console.log(createForm);
  };

  const handleSubmit = () => {
    console.log(email);

    linkRef.current.click();
  };

  // Create state to track whether overlay[Modal] is open or not
  const [isOverlayOpen, setIsOverlayOpen] = useState(false);
  // Arrow function to open the overlay
  const openOverlay = () => {
    console.log("openOverlay");
    setIsOverlayOpen(true);
  };
  // Arrow function to close the overlay
  const closeOverlay = () => {
    console.log("closeOverlay");
    setIsOverlayOpen(false);
  };

  return (
    <div className="login-div">
      <div className="show-overlay-container">
        {/* 
            // Boolean expression to check if overlay is open or not
        */}
        {isOverlayOpen && (
          <RegistrationOverlay2 onClose={closeOverlay} onSelectMethod />
        )}
      </div>

      <div className="login-screen-container">
        <div className="login-email-container">
          <div className="form-container">
            <form
              className="login-form"
              data-testid="login-form"
              onSubmit={(e) => {
                handleSubmit(e);
              }}
            >
              <h2 className="brand-logo">U-Event</h2>
              <label htmlFor="email"> E-mail </label>
              <input
                type="text"
                id="email"
                data-testid="email"
                name="email"
                required
                onChange={handleChange}
              />
              <label htmlFor="password"> Password </label>
              <input
                type="text"
                id="password"
                data-testid="password"
                name="password"
                required
                onChange={handleChange}
              />
              <button className="login-button" type="submit">
                {" "}
                Login{" "}
              </button>
              <button className="link-button" type="Forgot Password">
                {" "}
                Forgot Password{" "}
              </button>
              <button
                className="create-button"
                data-testid="create-account-button"
                type="Create Account"
                onClick={openOverlay}
              >
                Don't have an account! Create one here
              </button>
              {/* Auth Provider Buttons */}
              <div className="divider">
                <p className="custom-or-text">or</p>
                <hr />
              </div>
              <div className="button-container">
                <a
                  href="/"
                  className="google-btn"
                  onClick={(e) => {
                    e.preventDefault();
                  }}
                >
                  <FontAwesomeIcon className="btn-icon" icon={faGoogle} />
                </a>
                <a
                  href="/"
                  className="facebook-btn"
                  onClick={(e) => {
                    e.preventDefault();
                  }}
                >
                  <FontAwesomeIcon className="btn-icon" icon={faFacebook} />
                </a>
                <a
                  href="/"
                  className="google-btn"
                  onClick={(e) => {
                    e.preventDefault();
                  }}
                >
                  <FontAwesomeIcon className="btn-icon" icon={faApple} />
                </a>
              </div>
              {/* Auth Provider Buttons */}
            </form>
            <Link to="/" ref={linkRef} style={{ display: "none" }}></Link>
            <div className="login-image-container">
              {/* IMAGE */}

              {/* IMAGE */}
            </div>
          </div>
        </div>
      </div>

      <div>
        {email}
        {password}
      </div>
    </div>
  );
};

export default Login;
