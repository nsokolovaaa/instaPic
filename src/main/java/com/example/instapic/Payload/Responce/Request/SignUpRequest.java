package com.example.instapic.Payload.Responce.Request;

import com.example.instapic.Annotation.PasswordMatches;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignUpRequest {
    @Email(message = "It should have email ")
    @NotBlank(message = "User email is required")
    private String email;

    @NotEmpty(message = "Please, enter your userName")
    private String userName;

    @NotEmpty(message =  "Password  is required")
    @Size(min=8)
    private String password;

    @NotEmpty(message = "Please, enter your lastName")
    private String lastName;

    @NotEmpty(message = "Please, enter your FirstName")
    private String FirstName;

    private String ConfirmPassword;



}
