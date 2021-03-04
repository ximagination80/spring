package com.staxter.task2.service;

import com.staxter.task2.dto.UserLoginRequest;
import com.staxter.task2.dto.UserRegistrationRequest;
import com.staxter.task2.exception.IncorrectUserNamePasswordCombinationException;
import com.staxter.task2.exception.UserAlreadyExistsException;
import com.staxter.task2.repository.User;
import com.staxter.task2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SecurityService securityService;

    public long registerUser(UserRegistrationRequest request) throws UserAlreadyExistsException {
        String hashedPassword = securityService.encode(request.getPassword());

        User user = userRepository.createUser(new User(0,
                request.getFirstName(),
                request.getLastName(),
                request.getUserName(),
                hashedPassword));

        return user.getId();
    }

    public String login(UserLoginRequest request) throws IncorrectUserNamePasswordCombinationException {
        Optional<User> maybeUser = userRepository.findUserByUsername(request.getUserName());
        if (!maybeUser.isPresent()) {
            throw new IncorrectUserNamePasswordCombinationException();
        }

        User user = maybeUser.get();

        if (securityService.matches(user.getHashedPassword(), request.getPassword())) {
            throw new IncorrectUserNamePasswordCombinationException();
        }

        return securityService.generateToken(user.getUserName());
    }
}
