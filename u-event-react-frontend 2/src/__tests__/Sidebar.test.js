import React from "react";
import { render, fireEvent, screen } from "@testing-library/react";
import { BrowserRouter as Router } from "react-router-dom";
import Sidebar from "../components/Sidebar";
import "../css/Sidebar.css";

describe("Sidebar Component", () => {
  it("renders without errors", () => {
    render(
      <Router>
        <Sidebar />
      </Router>
    );
  });

  it("toggles sidebar visibility when the show button is clicked", () => {
    const { container } = render(
      <Router>
        <Sidebar />
      </Router>
    );

    // Initially, the sidebar is visible
    expect(container.querySelector(".hide")).toBeNull();

    // Click the show button
    fireEvent.click(screen.getByText("\u2190"));

    // Sidebar should be hidden
    expect(container.querySelector(".hide")).not.toBeNull();

    // Click the hide button
    fireEvent.click(screen.getByText("\u2192"));

    // Sidebar should be visible again
    expect(container.querySelector(".hide")).toBeNull();
  });

  it("renders interest pills", () => {
    render(
      <Router>
        <Sidebar />
      </Router>
    );

    // Check if the interest pills are rendered
    expect(screen.getByText("Rock Climbing")).toBeInTheDocument();
    expect(screen.getByText("Dancing")).toBeInTheDocument();
    expect(screen.getByText("Cooking")).toBeInTheDocument();
    expect(screen.getByText("Sports")).toBeInTheDocument();
    expect(screen.getByText("Hiking")).toBeInTheDocument();
  });

  // Add more tests for other functionality as needed
});
