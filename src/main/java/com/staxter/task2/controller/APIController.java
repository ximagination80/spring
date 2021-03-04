package com.staxter.task2.controller;


import com.staxter.task2.dto.*;
import com.staxter.task2.exception.IncorrectUserNamePasswordCombinationException;
import com.staxter.task2.exception.UserAlreadyExistsException;
import com.staxter.task2.repository.User;
import com.staxter.task2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class APIController {

    private final UserService userService;

    @PostMapping("/userservice/register")
    public UserRegistrationResponse register(@RequestBody @Valid UserRegistrationRequest request)
            throws UserAlreadyExistsException {
        long id = userService.registerUser(request);
        return new UserRegistrationResponse(id,
                request.getFirstName(),
                request.getLastName(),
                request.getUserName());
    }

    @PostMapping("/userservice/login")
    public UserLoginResponse login(@RequestBody @Valid UserLoginRequest request)
            throws IncorrectUserNamePasswordCombinationException {
        String token = userService.login(request);
        return new UserLoginResponse(token);
    }

    @GetMapping("/userservice/hello")
    public HelloDTO sayHelloForUser(User user) {
        return new HelloDTO(String.format("Hello %s!Welcome to staxter!", user.getFirstName()));
    }
}
