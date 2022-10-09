package com.example.instapic.Dto;

import com.example.instapic.Entity.Post;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private String userName;
    @NotEmpty
    private String message;
    private Long userId;
    private LocalDateTime createdDate;

}
