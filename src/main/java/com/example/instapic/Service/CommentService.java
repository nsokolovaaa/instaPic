package com.example.instapic.Service;

import com.example.instapic.Dto.CommentDto;
import com.example.instapic.Entity.Comment;
import com.example.instapic.Entity.Post;
import com.example.instapic.Entity.Users;
import com.example.instapic.Exceptions.PostNotFoundExeption;
import com.example.instapic.Exceptions.UserExistException;
import com.example.instapic.repository.CommentRepository;
import com.example.instapic.repository.PostRepository;
import com.example.instapic.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private static final org.slf4j.Logger Log = LoggerFactory.getLogger(CommentService.class);

    public CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostRepository postRepository;
    public UserRepository userRepository;


    public Comment saveComment(Long postId, CommentDto commentDto, Principal principal) {
        Users user = getUsersByPrincipal(principal);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundExeption("Post cannot be found for username"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setMessage(commentDto.getMessage());
        comment.setUserId(commentDto.getUserId());
        comment.setUserName(commentDto.getUserName());
        Log.info("Comment for user was create!");
        return commentRepository.save(comment);

    }

    public Users getUsersByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUsersByUsername(username)
                .orElseThrow(() -> new UserExistException("Use didn't with username" + username));

    }
    public List<Comment> getAllComment (Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundExeption("Post cannot be found"));
        List<Comment> comments = commentRepository.findAllByPost(post);
        return comments;

    }
    public void delete(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository :: delete);
    }
}
