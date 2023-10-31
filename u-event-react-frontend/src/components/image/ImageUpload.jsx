import React, { useState } from "react";

function ImageUpload() {
  const [file, setFile] = useState(null);

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const handleUpload = () => {
    if (file) {
      const formData = new FormData();
      formData.append("file", file);

      fetch("http://localhost:8080/images/uploadImage", {
        method: "POST",
        body: formData,
      })
        .then((response) => {
          if (response.ok) {
            // Image uploaded successfully, you can handle the success here
          } else {
            // Handle the error here
          }
        })
        .catch((error) => {
          console.error("Error uploading image:", error);
        });
    }
  };
  
  return (
    <div
      style={{
        zIndex: "9999",
        left: "50%",
        textAlign: "center",
        margin: "1rem",
      }}
    >
      <input type="file" onChange={handleFileChange} />
      <button onClick={handleUpload}>Upload Image</button>
    </div>
  );
}

export default ImageUpload;
