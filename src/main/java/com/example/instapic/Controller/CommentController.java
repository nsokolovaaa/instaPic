package com.example.instapic.Controller;

import com.example.instapic.Dto.CommentDto;
import com.example.instapic.Entity.Comment;
import com.example.instapic.Facade.CommentFacade;
import com.example.instapic.Payload.Responce.MessagesResponse;
import com.example.instapic.Payload.Responce.ResponseErrorValidation;
import com.example.instapic.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/comment")
@CrossOrigin
public class CommentController {
    @Autowired
    private CommentFacade commentFacade;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    @PostMapping("/{postId}/create")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDto commentDto,
                                                @PathVariable("postId") String postId,
                                                BindingResult bindingResult,
                                                Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        Comment comment = commentService.saveComment(Long.parseLong(postId), commentDto, principal);
        CommentDto createdComment = commentFacade.CommentToCommentDto(comment);
        return new ResponseEntity<>(createdComment, HttpStatus.OK);
    }
   @GetMapping("/{postId}/all")
   public ResponseEntity<List<CommentDto>> getAllComment(@PathVariable("postId") String postId){
        List<CommentDto> commentDtos = commentService.getAllComment(Long.parseLong(postId))
                .stream()
                .map(commentFacade ::CommentToCommentDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
   }
   @PostMapping("/{commenyId}/delete")
   public ResponseEntity<MessagesResponse> delete(@PathVariable("commentId") String commentId){
        commentService.delete(Long.parseLong(commentId));
       return new ResponseEntity<>(new MessagesResponse("Comment was deleted"), HttpStatus.OK);


   }



}
