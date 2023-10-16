import React from "react";
import { render, fireEvent, screen, waitFor } from "@testing-library/react";
import CreateEvent from "../components/mainui/CreateEvent";
// import sinon from "sinon"; // Add this import for Sinon
import { expect } from "chai"; // Add this import for Chai

describe("CreateEvent Component", () => {
  it("renders without crashing", () => {
    render(<CreateEvent />);
  });

  it("updates the form fields correctly", async () => {
    render(<CreateEvent />);

    // Get form elements
    const titleInput = screen.getByLabelText("Title");
    const hostInput = screen.getByLabelText("Host");
    const locationInput = screen.getByLabelText("Location");
    const timeSelect = screen.getByLabelText("Time");
    const durationSelect = screen.getByLabelText("Duration");
    const descriptionTextarea = screen.getByLabelText("Description");

    // Simulate user input
    fireEvent.change(titleInput, { target: { value: "Test Event" } });
    fireEvent.change(hostInput, { target: { value: "Test Host" } });
    fireEvent.change(locationInput, { target: { value: "Test Location" } });
    fireEvent.change(timeSelect, { target: { value: "2:00pm" } });
    fireEvent.change(durationSelect, { target: { value: "2" } });
    fireEvent.change(descriptionTextarea, {
      target: { value: "Test Description" },
    });

    // Assert that the form fields have been updated
    expect(titleInput.value).equal("Test Event");
    expect(hostInput.value).equal("Test Host");
    expect(locationInput.value).equal("Test Location");
    expect(timeSelect.value).equal("2:00pm");
    expect(durationSelect.value).equal("2");
    expect(descriptionTextarea.value).equal("Test Description");
  });

  it('displays a notification when the "Create Event" button is clicked', async () => {
    render(<CreateEvent />);

    // Get the "Create Event" button by the data-testid attribute
    const createEventButton = screen.getByTestId("submit-btn");

    // Click the "Create Event" button
    fireEvent.click(createEventButton);

    // Wait for the notification to appear - Meaning that the event has been created.

    const notification = screen.getByTestId("notification");
    expect(notification).to.exist; // Check the notification text
  });
});
