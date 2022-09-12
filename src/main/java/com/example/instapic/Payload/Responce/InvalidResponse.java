package com.example.instapic.Payload.Responce;

import lombok.Getter;

@Getter
public class InvalidResponse {
    private String userName;
    private String password;


    public InvalidResponse() {
        this.userName = "Invalid userName";
        this.password = "Invalid password";
    }
}
