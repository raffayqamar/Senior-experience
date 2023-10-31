import React from "react";
import EventBoard from "./EventBoard";
import "../../css/MainUI.css";

const MainUI = () => {
  return (
    <section className="main-ui">
      {/* <img src={rectImageOne} className="bg-rec-image" alt="rectimage" /> */}{" "}
      {/* rect image */}
      <EventBoard />
    </section>
  );
};

export default MainUI;
