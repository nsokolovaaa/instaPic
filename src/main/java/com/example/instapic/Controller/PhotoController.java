package com.example.instapic.Controller;

import com.example.instapic.Entity.Photo;
import com.example.instapic.Payload.Responce.MessagesResponse;
import com.example.instapic.Service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("api/photo")
@CrossOrigin
public class PhotoController {
    @Autowired
    private PhotoService photoService;
    @PostMapping("/upload")
    public ResponseEntity<MessagesResponse> uploadImage(@RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        photoService.uploadPhotoToUser(file, principal);
        return ResponseEntity.ok(new MessagesResponse("Photo upload successfully"));
    }
    @PostMapping("/{postId}/upload")
    public ResponseEntity<MessagesResponse> uploadImage(@PathVariable("postId") String postId,
                                                        @RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        photoService.uploadPhotoToPost(file, principal, Long.parseLong(postId));
        return ResponseEntity.ok(new MessagesResponse("Photo upload successfully"));
    }
    @GetMapping("/profileImage")
    public ResponseEntity<Photo> getImageForUser(Principal principal){
        Photo photo = photoService.getPhotoToUser(principal);
        return new ResponseEntity<>(photo, HttpStatus.OK);
    }
    @GetMapping("/{postId}/photo")
    public ResponseEntity<Photo> getImageForPost(@PathVariable("postId") String postId){
        Photo photo = photoService.getPhotoToPost(Long.parseLong(postId));
        return new ResponseEntity<>(photo, HttpStatus.OK);
    }

    }



