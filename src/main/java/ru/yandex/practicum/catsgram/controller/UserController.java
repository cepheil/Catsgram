package ru.yandex.practicum.catsgram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.dto.NewUserRequest;
import ru.yandex.practicum.catsgram.dto.UpdateUserRequest;
import ru.yandex.practicum.catsgram.dto.UserDto;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody NewUserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable("userId") long userId, @RequestBody UpdateUserRequest request) {
        return userService.updateUser(userId, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable("userId") long userId) {
        return userService.getUserById(userId);
    }
}





//package ru.yandex.practicum.catsgram.controller;
//
////import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import ru.yandex.practicum.catsgram.model.User;
//import ru.yandex.practicum.catsgram.service.UserService;
//
//import java.util.Collection;
//import java.util.Optional;
//
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//    private final UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/{userId}")
//    public Optional<User> findById(@PathVariable long userId) {
//        return userService.findUserById(userId);
//    }
//
//    @GetMapping
//    public Collection<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public User addUser(@RequestBody User user) {
//        return userService.addUser(user);
//    }
//
//    @PutMapping
//    public User updateUser(@RequestBody User newUser) {
//        return userService.updateUser(newUser);
//    }
//
//
//}
