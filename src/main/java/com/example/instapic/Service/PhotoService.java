package com.example.instapic.Service;

import com.example.instapic.Entity.Photo;
import com.example.instapic.Entity.Post;
import com.example.instapic.Entity.Users;
import com.example.instapic.Exceptions.PhotoNotFoundExeption;
import com.example.instapic.Exceptions.PostNotFoundExeption;
import com.example.instapic.Exceptions.UserExistException;
import com.example.instapic.repository.PhotoRepository;
import com.example.instapic.repository.PostRepository;
import com.example.instapic.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class PhotoService {
    private static final org.slf4j.Logger Log = LoggerFactory.getLogger(PhotoService.class);

    private PhotoRepository photoRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository, PostRepository postRepository, UserRepository userRepository) {
        this.photoRepository = photoRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    public Photo uploadPhotoToUser(MultipartFile file, Principal principal) throws IOException {
        Users users = getUsersByPrincipal(principal);
        Log.info("Uploading image profile  to user {} ", users.getUsername());
        Photo uploadPhoto = photoRepository.findByUsersId(users.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(uploadPhoto)) {
            photoRepository.delete(uploadPhoto);
        }
        Photo photo = new Photo();
        photo.setId(users.getId());
        photo.setImage(compressBytes(file.getBytes()));
        photo.setName(file.getOriginalFilename());
        return photoRepository.save(photo);
    }

    public Photo uploadPhotoToPost(MultipartFile file, Principal principal, Long postId) throws IOException {
        Users user = getUsersByPrincipal(principal);
        Post post = user.getPosts()
                .stream()
                .filter(p -> p.getId().equals(postId))
                .collect(toSingleCollector());
        Photo photo = new Photo();
        photo.setPostId(post.getId());
        photo.setImage(file.getBytes());
        photo.setName(file.getOriginalFilename());
        Log.info("Uploading photo to Post {}", post.getId());
        return photoRepository.save(photo);
    }

    public Photo getPhotoToUser(Principal principal) {
        Users user = getUsersByPrincipal(principal);
        Photo photo = photoRepository.findByUsersId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(photo)) {
            photo.setImage(decompressBytes(photo.getImage()));
        }
        return photo;
    }

    public Photo getPhotoToPost(Long postId) {
        Photo photo = photoRepository.findPostById(postId)
                .orElseThrow(() -> new PhotoNotFoundExeption("Cannot find to post"));
        if (!ObjectUtils.isEmpty(photo)) {
            photo.setImage(decompressBytes(photo.getImage()));
        }
        return photo;
    }

    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            Log.error("Cannot compress bytes");
        }
        System.out.println("Compressed Image bytes size" + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public Users getUsersByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUsersByUsername(username)
                .orElseThrow(() -> new UserExistException("Use didn't with username" + username));
    }


    private static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
        } catch (DataFormatException e) {
            Log.error("Cannot decompress bytes");
        }
        return outputStream.toByteArray();
    }

    private <T> Collector<T, ?, T> toSingleCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }
}








