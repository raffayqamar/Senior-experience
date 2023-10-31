package com.cs4360msudenver.ueventspringbootbackend.image;

import com.cs4360msudenver.ueventspringbootbackend.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/images")
public class ImageController {



    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity<?> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImageById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(imageService.getImageById(id));
    }

    @PostMapping("/uploadImage/{username}")
    public ResponseEntity<String> saveProduct(@RequestParam("file") MultipartFile file, @PathVariable("username") String username) {
        try {
            imageService.saveImageToDB(file, username);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (Exception e) {
            // Handle the exception and return an error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    // Custom controller method to get an image by username
    @GetMapping("/image/{username}")
    public ResponseEntity<List<Long>> getImageIdByUsername(@PathVariable("username") String username) {
//        List<Long> imageId = imageService.findIdsByUsername(username);

        // Return the first image ID found for the given username
        List<Long> imageId = Collections.singletonList(imageService.findIdsByUsername(username).get(0));
        if (imageId != null) {
            return ResponseEntity.ok(imageId);
        } else {
            // Handle the case where no image ID was found for the given username
            return ResponseEntity.notFound().build();
        }
    }

//    Delete Image
    @DeleteMapping("/deleteImage/{id}")
    public ResponseEntity<HttpStatus> deleteImage(@PathVariable("id") Long id) {
        Image image = imageService.getImageById(id);
        try {
            imageService.deleteImage(image.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}