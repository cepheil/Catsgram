package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;


@Data
@EqualsAndHashCode(of = { "email" })
public class User {
    private String email;
    private Long id;
    private String username;
    private String password;
    private Instant registrationDate;

}
