package ru.yandex.practicum.catsgram.service;


import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.DuplicatedDataException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.model.User;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final Map<Long, User> users = new HashMap<>();

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public User addUser(User user) {
        // проверяем выполнение необходимых условий
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }

        for (User u : users.values()) {
            if (u.getEmail().equals(user.getEmail())) {
                throw new DuplicatedDataException("Этот имейл уже используется");
            }
        }

        user.setId(getNextId());
        user.setRegistrationDate(Instant.now());
        users.put(user.getId(), user);
        return user;
    }

    public User updateUser(User newUser) {
        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        User existingUser = users.get(newUser.getId());
        if (existingUser == null) {
            throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
        }
        if (newUser.getEmail() != null && !newUser.getEmail().equals(existingUser.getEmail())) {
            for (User u : users.values()) {
                if (!u.getId().equals(existingUser.getId()) && u.getEmail().equals(newUser.getEmail())) {
                    throw new DuplicatedDataException("Этот имейл уже используется");
                }
            }
            existingUser.setEmail(newUser.getEmail());
        }
        if (newUser.getUsername() != null) {
            existingUser.setUsername(newUser.getUsername());
        }
        if (newUser.getPassword() != null) {
            existingUser.setPassword(newUser.getPassword());
        }
        return existingUser;
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }


    public Optional<User> findUserById(long id) {
        if (users.containsKey(id)) {
            return Optional.of(users.get(id));
        }
        return Optional.empty();
    }
    //return Optional.ofNullable(users.get(id));

}
