import React, { useEffect, useState } from "react";

function ImageDisplay() {
  const [imageBase64, setImageBase64] = useState(null);

  const username = localStorage.getItem("username");

  useEffect(() => {
    fetch(`http://localhost:8080/users/${username}`)
      .then((response) => {
        if (response.ok) {
          return response.json(); // Parse the JSON response
        } else {
          throw new Error("Failed to fetch image");
        }
      })
      .then((data) => {
        // Extract the base64 image data from the JSON response
        const base64String = data.image;
        console.log(data);

        setImageBase64(base64String);
      })
      .catch((error) => {
        console.error("Error fetching image:", error);
      });
  }, []);

  return (
    <div
      style={{
        zIndex: "9999",
        position: "absolute",
        left: "50%",
        transform: "translate(-50%, -50%)",
      }}
    >
      {imageBase64 && (
        <img
          src={`data:image/jpeg;base64,${imageBase64}`}
          width={100}
          height={100}
          alt="Image"
        />
      )}
    </div>
  );
}

export default ImageDisplay;
