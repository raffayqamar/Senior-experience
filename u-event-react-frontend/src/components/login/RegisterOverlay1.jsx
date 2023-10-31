import React, { useState } from "react";
import RegistrationOverlay2 from "./RegisterOverlay2";

/* Overlay to allow user to select method of registration */

const RegistrationOverlay1 = ({ onClose, onSelectMethod }) => {
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
        <form className="overlay1-form">
          <h2> Create Account </h2>
          <p>
            By Creating an Account You agree to the Terms of Service and the
            Privacy Policy{" "}
          </p>
          <div className="overlay1-button-container">
            <button
              className="overlay1-button"
              onClick={() => onSelectMethod("google")}
            >
              {" "}
              Continue with Google{" "}
            </button>
            <button
              className="overlay1-button"
              onClick={() => onSelectMethod("facebook")}
            >
              {" "}
              Continue with Facebook{" "}
            </button>
            <button
              className="overlay1-button"
              onClick={() => onSelectMethod("apple")}
            >
              {" "}
              Continue with Apple{" "}
            </button>
            <button
              className="overlay1-button"
              onClick={(e) => {
                // onSelectMethod("email");
                e.preventDefault();
                openOverlay();
              }}
            >
              {" "}
              Continue with Email{" "}
            </button>
            {/* Open the inner overlay for the form signup manually entry 
            - Boolean value is passed to the inner overlay to determine which form to display
            */}
            {
              isOverlayOpen && (
                <RegistrationOverlay2 onClose={closeOverlay} onSelectMethod />
              )
              // <RegistrationOverlay2 onClose={closeOverlay} onSelectMethod />
            }
          </div>
          <div className="checkbox-container">
            <label htmlFor="subscribeToEmails">
              {" "}
              Subscribe to Email and Product Details{" "}
            </label>
            <input
              type="checkbox"
              checked={subscribeToEmails}
              onChange={handleToggleEmailSubscription}
            />
          </div>
        </form>
      </div>
    </div>
  );
};

export default RegistrationOverlay1;
