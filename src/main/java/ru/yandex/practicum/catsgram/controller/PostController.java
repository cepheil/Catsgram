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


//package ru.yandex.practicum.catsgram.controller;
//
////import lombok.RequiredArgsConstructor;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//        import ru.yandex.practicum.catsgram.exception.ParameterNotValidException;
//import ru.yandex.practicum.catsgram.model.Post;
//import ru.yandex.practicum.catsgram.model.SortOrder;
//import ru.yandex.practicum.catsgram.service.PostService;
//
//import java.util.Collection;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/posts")
//public class PostController {
//    private final PostService postService;
//
//    @Autowired
//    public PostController(PostService postService) {
//        this.postService = postService;
//    }
//
//    @GetMapping
//    public Collection<Post> findAll(
//            @RequestParam(defaultValue = "asc") String sort,
//            @RequestParam(defaultValue = "0") int from,
//            @RequestParam(defaultValue = "10") int size) {
//
//        SortOrder sortOrder = SortOrder.from(sort);
//        if (sortOrder == null) {
//            throw new ParameterNotValidException("sort", "Получено: " + sort + " должно быть: ask или desc");
//        }
//        if (size <= 0) {
//            throw new ParameterNotValidException("size", "Размер должен быть больше нуля");
//        }
//        if (from < 0) {
//            throw new ParameterNotValidException("from", "Начало выборки должно быть положительным числом");
//        }
//
//        return postService.findAll(sortOrder, from, size);
//    }
//
//
//    @GetMapping("/{postId}")
//    public Optional<Post> findById(@PathVariable long postId) {
//        return postService.findById(postId);
//    }
//
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Post create(@RequestBody Post post) {
//        return postService.create(post);
//    }
////    public ResponseEntity<Post> create(@RequestBody Post post) {
////        Post createdPost = postService.create(post);
////        return ResponseEntity
////                // указываем код статуса
////                .status(HttpStatus.CREATED)
////                // указываем тело ответа — эта операция завершает создание ResponseEntity
////                .body(createdPost);
////    }
//
//
//    @PutMapping
//    public Post update(@RequestBody Post newPost) {
//        return postService.update(newPost);
//    }
//}