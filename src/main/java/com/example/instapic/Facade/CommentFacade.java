package com.example.instapic.Facade;

import com.example.instapic.Dto.CommentDto;
import com.example.instapic.Entity.Comment;
import org.springframework.stereotype.Component;


@Component
public class CommentFacade {
    public CommentDto CommentToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setUserName(comment.getUserName());
        commentDto.setMessage(comment.getMessage());
        commentDto.setUserId(comment.getUserId());
        commentDto.setCreatedDate(comment.getCreatedDate());
      return  commentDto;

    }

    }
