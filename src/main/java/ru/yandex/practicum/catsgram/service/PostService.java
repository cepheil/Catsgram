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
