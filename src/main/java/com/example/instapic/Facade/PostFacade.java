package com.example.instapic.Facade;

import com.example.instapic.Dto.PostDto;
import com.example.instapic.Dto.UsersDto;
import com.example.instapic.Entity.Post;
import com.example.instapic.Entity.Users;
import org.springframework.stereotype.Component;

@Component
public class PostFacade{
    public PostDto PostToPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setLikes(post.getLikes());
        postDto.setCaption(post.getCaption());
        postDto.setTitle(post.getTitle());
        postDto.setUserLike(post.getLikedUsers());
        postDto.setUserName(post.getUsers().getUsername());
        return postDto;

    }
}
