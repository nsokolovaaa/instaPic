package com.example.instapic.Controller;

import com.example.instapic.Payload.Responce.JWTTokenSuccesResponse;
import com.example.instapic.Payload.Responce.MessagesResponse;
import com.example.instapic.Payload.Responce.Request.LoginRequest;
import com.example.instapic.Payload.Responce.Request.SignUpRequest;
import com.example.instapic.Payload.Responce.ResponseErrorValidation;
import com.example.instapic.Service.UserService;
import com.example.instapic.security.JWTTokenProvider;
import com.example.instapic.security.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {

    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;
    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUserName(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstant.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTTokenSuccesResponse(true, jwt));
    }


    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signupRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        userService.create(signupRequest);
        return ResponseEntity.ok(new MessagesResponse("The User has successfully registered!"));
    }

}