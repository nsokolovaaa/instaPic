package com.example.instapic.Payload.Responce.Request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {
    @NotEmpty(message = "User cannot be empty")
    private String userName;
    @NotEmpty(message = "Password cannot be empty")
    private String password;

}
