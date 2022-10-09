package com.example.instapic.Dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UsersDto {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String username;
    private String  bio;
    @NotEmpty
    private String lastName;
    private String email;
    @NotEmpty
    private String password;

}
