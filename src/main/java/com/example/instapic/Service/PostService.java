package com.example.instapic.Service;

import com.example.instapic.Dto.PostDto;
import com.example.instapic.Entity.Photo;
import com.example.instapic.Entity.Post;
import com.example.instapic.Entity.Users;
import com.example.instapic.Exceptions.PostNotFoundExeption;
import com.example.instapic.Exceptions.UserExistException;
import com.example.instapic.repository.PhotoRepository;
import com.example.instapic.repository.PostRepository;
import com.example.instapic.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private static final org.slf4j.Logger Log = LoggerFactory.getLogger(PostService.class);
    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final PhotoRepository photoRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository, PhotoRepository photoRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.photoRepository = photoRepository;
    }

    public Post createPost(PostDto postDto, Principal principal) {
        Users users = getUsersByPrincipal(principal);
        Post post = new Post();
        post.setUsers(users);
        post.setCaption(postDto.getCaption());
        post.setLocation(postDto.getLocation());
        post.setLikes(0);
        post.setTitle(postDto.getTitle());
        Log.info("Saving post for User : {}" + users.getEmail());

        return postRepository.save(post);

    }

    public List<Post> getAllPost() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    public Post getPostById(Long postId, Principal principal) {
        Users users = getUsersByPrincipal(principal);
        return postRepository.findPostByIdAndUsers(postId, users)
                .orElseThrow(() -> new PostNotFoundExeption(""));

    }

    public Users getUsersByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUsersByUsername(username)
                .orElseThrow(() -> new UserExistException("Use didn't with username" + username));

    }

    public List<Post> getAllPostOfUser(Principal principal) {
        Users users = getUsersByPrincipal(principal);
        return postRepository.findAllByUsersOrderByCreatedDateDesc(users);
    }

    public Post likePost(Long postId, String userName) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundExeption("Post cannot be found!"));


        Optional<String> userLiked = post.getLikedUsers()
                .stream().filter(u -> u.equals(userName)).findAny();

        if (userLiked.isPresent()) {
            post.setLikes(post.getLikes() - 1);
            post.getLikedUsers().remove(userName);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getLikedUsers().add(userName);
        }
        return postRepository.save(post);

    }
    public void deletePost(Long postId, Principal principal) {
        Post post = getPostById(postId, principal);
        Optional<Photo> photo = photoRepository.findPostById(post.getId());
        postRepository.delete(post);
        photo.ifPresent(photoRepository:: delete);
    }

}

