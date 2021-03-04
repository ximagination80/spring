package com.staxter.task2.repository;

import com.staxter.task2.exception.UserAlreadyExistsException;

import java.util.Optional;

public interface UserRepository {

    User createUser(User user) throws UserAlreadyExistsException;

    Optional<User> findUserByUsername(String userName);

    void clear();
}
