package com.staxter.task2.repository;

import com.staxter.task2.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final Object monitor = new Object();
    private final AtomicLong pkFactory = new AtomicLong(0);
    private final ConcurrentHashMap<String, User> table = new ConcurrentHashMap<>();

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {
        String userName = user.getUserName();

        synchronized (monitor) {
            if (findUserByUsername(userName).isPresent()){
                throw new UserAlreadyExistsException();
            }
            user.setId(pkFactory.incrementAndGet());
            table.put(userName, user);
        }

        return user;
    }

    @Override
    public Optional<User> findUserByUsername(String userName) {
        return Optional.ofNullable(table.get(userName));
    }

    @Override
    public void clear() {
        table.clear();
    }
}
