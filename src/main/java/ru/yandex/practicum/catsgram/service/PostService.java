package ru.yandex.practicum.catsgram.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.dal.PostRepository;
import ru.yandex.practicum.catsgram.dal.UserRepository;
import ru.yandex.practicum.catsgram.dto.NewPostRequest;
import ru.yandex.practicum.catsgram.dto.PostDto;
import ru.yandex.practicum.catsgram.dto.UpdatePostRequest;
import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
import ru.yandex.practicum.catsgram.exception.NotFoundException;
import ru.yandex.practicum.catsgram.mapper.PostMapper;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;
import java.util.stream.Collectors;

// Указываем, что класс PostService - является бином и его
// нужно добавить в контекст приложения
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public PostDto createPost(NewPostRequest request) {
        if (request.getAuthorId() == null) {
            throw new ConditionsNotMetException("ID автора должен быть указан");
        }

        if (request.getDescription() == null || request.getDescription().isBlank()) {
            throw new ConditionsNotMetException("Описание не может быть пустым");
        }

        User author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + request.getAuthorId() + " не найден"));

        Post post = PostMapper.mapToPost(request);
        post = postRepository.save(post);

        return PostMapper.mapToPostDto(post);

    }


    public PostDto getPostById(long postId) {
        return postRepository.findById(postId)
                .map(PostMapper::mapToPostDto)
                .orElseThrow(() -> new NotFoundException("Пост не найден с ID: " + postId));

    }


    public List<PostDto> getPostsByAuthorId(long authorId) {
        return postRepository.findByAuthorId(authorId)
                .stream()
                .map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());
    }


    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());

    }


    public PostDto updatePost(long postId, UpdatePostRequest request) {
        Post updPost = postRepository.findById(postId)
                .map(post -> PostMapper.updatePostFields(post, request))
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        updPost = postRepository.update(updPost);
        return PostMapper.mapToPostDto(updPost);

    }

}


//package ru.yandex.practicum.catsgram.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import ru.yandex.practicum.catsgram.exception.ConditionsNotMetException;
//import ru.yandex.practicum.catsgram.exception.NotFoundException;
//import ru.yandex.practicum.catsgram.model.Post;
//import ru.yandex.practicum.catsgram.model.SortOrder;
//
//import java.time.Instant;
//import java.util.*;
//
//// Указываем, что класс PostService - является бином и его
//// нужно добавить в контекст приложения
//@Service
//public class PostService {
//    private final Map<Long, Post> posts = new HashMap<>();
//    private final Comparator<Post> postDateComparator = Comparator.comparing(Post::getPostDate);
//    private final UserService userService;
//
//
//    @Autowired
//    public PostService(UserService userService) {
//        this.userService = userService;
//    }
//
//
//    public Collection<Post> findAll(SortOrder sort, int from, int size) {
//        return  posts.values().stream()
//                .sorted(sort.equals(SortOrder.ASCENDING) ?
//                        postDateComparator : postDateComparator.reversed())
//                .skip(from)
//                .limit(size)
//                .toList();
//    }
//
//
//    public Post create(Post post) {
//        if (post.getAuthorId() == null || userService.findUserById(post.getAuthorId()).isEmpty()) {
//            throw new ConditionsNotMetException("Автор с id = " + post.getAuthorId() + " не найден");
//        }
//
//        if (post.getDescription() == null || post.getDescription().isBlank()) {
//            throw new ConditionsNotMetException("Описание не может быть пустым");
//        }
//
//        post.setId(getNextId());
//        post.setPostDate(Instant.now());
//        posts.put(post.getId(), post);
//        return post;
//    }
//
//    public Post update(Post newPost) {
//        if (newPost.getId() == null) {
//            throw new ConditionsNotMetException("Id должен быть указан");
//        }
//        if (posts.containsKey(newPost.getId())) {
//            Post oldPost = posts.get(newPost.getId());
//            if (newPost.getDescription() == null || newPost.getDescription().isBlank()) {
//                throw new ConditionsNotMetException("Описание не может быть пустым");
//            }
//            oldPost.setDescription(newPost.getDescription());
//            return oldPost;
//        }
//        throw new NotFoundException("Пост с id = " + newPost.getId() + " не найден");
//    }
//
//
//    public Optional<Post> findById(long postId) {
//        return Optional.ofNullable(posts.get(postId));
//    }
//
//
//    private long getNextId() {
//        long currentMaxId = posts.keySet()
//                .stream()
//                .mapToLong(id -> id)
//                .max()
//                .orElse(0);
//        return ++currentMaxId;
//    }
//}