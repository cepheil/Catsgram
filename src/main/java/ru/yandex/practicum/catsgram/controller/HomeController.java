package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {


    // @GetMapping, @PostMapping, @PutMapping, @DeleteMapping и @PatchMapping
    @GetMapping
    public String homePage() {
        return "<h1>Приветствуем вас, в приложении Котограм<h1>";
    }
}