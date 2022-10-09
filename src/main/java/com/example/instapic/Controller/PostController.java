package com.example.instapic.Controller;

import com.example.instapic.Dto.PostDto;
import com.example.instapic.Entity.Post;
import com.example.instapic.Facade.PostFacade;
import com.example.instapic.Payload.Responce.MessagesResponse;
import com.example.instapic.Payload.Responce.ResponseErrorValidation;
import com.example.instapic.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.MessageDigest;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/post")
@CrossOrigin
public class PostController {
    @Autowired
    private PostFacade postFacade;
    @Autowired
    private PostService postService;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;
    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto,
                                             BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        Post post = postService.createPost(postDto, principal);
        PostDto createdPost = postFacade.PostToPostDto(post);
        return new ResponseEntity<>(createdPost, HttpStatus.OK);

    }
    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPost(){
        List<PostDto> postDtos = postService.getAllPost()
                .stream()
                .map(postFacade :: PostToPostDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postDtos, HttpStatus.OK);

    }
    @GetMapping("users/posts")
    public ResponseEntity<List<PostDto>> getAllPostForUser(Principal principal){
        List<PostDto> postDtos = postService.getAllPostOfUser(principal)
                .stream()
                .map(postFacade  ::PostToPostDto )
                .collect(Collectors.toList());
        return new ResponseEntity<>(postDtos, HttpStatus.OK);

    }
    @PostMapping("/{postId}/{username}/like")
    public ResponseEntity<PostDto> likePosts(@PathVariable("postId") String postId,
                                                   @PathVariable("username") String username){
        Post post = postService.likePost(Long.parseLong(postId), username);
        PostDto postDto = postFacade.PostToPostDto(post);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }
    @PostMapping("/{postId}/delete")
    public ResponseEntity<MessagesResponse> deletePost(@PathVariable("postId") String postId,
                                                     Principal principal){
        postService.deletePost(Long.parseLong(postId), principal);
        return new ResponseEntity<>(new MessagesResponse("Post was deleted"), HttpStatus.OK);
    }

}
