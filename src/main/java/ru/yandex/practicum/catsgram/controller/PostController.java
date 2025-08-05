package ru.yandex.practicum.catsgram.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.dto.NewPostRequest;
import ru.yandex.practicum.catsgram.dto.PostDto;
import ru.yandex.practicum.catsgram.dto.UpdatePostRequest;
import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto create(@RequestBody NewPostRequest postRequest) {
        return postService.createPost(postRequest);
    }


    @PutMapping("/{postId}")
    public PostDto update(@PathVariable("postId") long postId, @RequestBody UpdatePostRequest request) {
        return postService.updatePost(postId, request);
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostDto getPostById(@PathVariable("postId") long postId) {
        return postService.getPostById(postId);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/by-author")
    @ResponseStatus(HttpStatus.OK)
    public List<PostDto> getPostsByAuthor(
            @RequestParam(value = "authorId", defaultValue = "-1") long authorId) {

        if (authorId == -1) {
            throw new ParameterNotValidException("authorId", "ID автора должно быть указано");
        }

        return postService.getPostsByAuthorId(authorId);
    }

}
