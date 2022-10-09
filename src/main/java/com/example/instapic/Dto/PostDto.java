package com.example.instapic.Dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String location;
    private Integer likes;
    private String caption;
    private Set<String> userLike;
    private String userName;

}
