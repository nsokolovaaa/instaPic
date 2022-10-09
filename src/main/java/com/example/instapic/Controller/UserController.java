package com.example.instapic.Controller;

import com.example.instapic.Dto.UsersDto;
import com.example.instapic.Entity.Users;
import com.example.instapic.Facade.UserFacade;
import com.example.instapic.Payload.Responce.ResponseErrorValidation;
import com.example.instapic.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    private ResponseEntity<UsersDto> getCurrentUser(Principal principal) {
        Users users = userService.getCurrentUsers(principal);
        UsersDto userDto = userFacade.usersToUsersDto(users);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{userId}/")
    public ResponseEntity<UsersDto> getUserProfile(@PathVariable("userId") String userId) {
        Users users = userService.getUserById(Long.parseLong(userId));
        UsersDto usersDto = userFacade.usersToUsersDto(users);
        return new ResponseEntity<>(usersDto, HttpStatus.OK);

    }
    @PostMapping("/update/")

    public ResponseEntity<Object> updateUser(@Valid @RequestBody UsersDto usersDto, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        Users users = userService.update(usersDto, principal);
        UsersDto updateUser = userFacade.usersToUsersDto(users);
        return  new ResponseEntity<>(updateUser, HttpStatus.OK);
    }
}