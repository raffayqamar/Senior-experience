import React from "react";
import { expect } from "chai";
import { render, fireEvent, screen } from "@testing-library/react";
import sinon from "sinon";
import Login from "../components/login/Login";
import RegistrationOverlay1 from "../components/login/RegisterOverlay1";

describe("Login Component", () => {
  it("should render without errors", () => {
    const { container } = render(<Login />);
    const loginDiv = container.querySelector(".login-div");
    expect(loginDiv).to.exist;
  });

  it("should handle email and password input changes", () => {
    const { getByTestId } = render(<Login />);
    const emailInput = getByTestId("email");
    const passwordInput = getByTestId("password");

    fireEvent.change(emailInput, { target: { value: "test@example.com" } });
    fireEvent.change(passwordInput, { target: { value: "password123" } });

    expect(emailInput.value).to.equal("test@example.com");
    expect(passwordInput.value).to.equal("password123");
  });

  it("should call handleSubmit when the form is submitted", () => {
    const handleSubmit = sinon.stub();
    const { getByTestId } = render(<Login handleSubmit={handleSubmit} />);
    const form = getByTestId("login-form");

    fireEvent.submit(form);
    expect(handleSubmit.calledOnce).to.be.true;
  });

  it("should open RegistrationOverlay1 when 'Create Account' button is clicked", () => {
    const { getByTestId } = render(<Login />);
    // const { getByText } = render(<RegistrationOverlay1 />);
    const createAccountButton = getByTestId("create-account-button");

    fireEvent.click(createAccountButton);

    expect(screen.queryByTestId("close-overlay-button")).to.exist;
    
  });

  it("should close RegistrationOverlay1 when onClose is called", () => {
    const { getByTestId } = render(<Login />);
    const createAccountButton = getByTestId("create-account-button");

    fireEvent.click(createAccountButton);

    const closeButton = screen.getByTestId("close-overlay-button");
    fireEvent.click(closeButton);

    expect(screen.queryByText("RegistrationOverlay1")).not.to.exist;
  });
});