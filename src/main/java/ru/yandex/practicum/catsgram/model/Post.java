package ru.yandex.practicum.catsgram.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.List;


@Data
@EqualsAndHashCode(of = {"id"})
public class Post {
    private Long id;
    private Long authorId;
    private String description;
    private Instant postDate;
    private List<Image> photos;
}
