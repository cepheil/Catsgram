package ru.yandex.practicum.catsgram.mapper;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.catsgram.dto.ImageDto;
import ru.yandex.practicum.catsgram.dto.ImageUploadResponse;
import ru.yandex.practicum.catsgram.model.Image;

import java.nio.file.Path;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ImageMapper {

    public static Image mapToImage(long postId, String originalFileName , Path savedPath) {
        Image image = new Image();
        image.setPostId(postId);
        image.setOriginalFileName(originalFileName);
        image.setFilePath(savedPath.toString());
        return image;
    }

    public static ImageDto imageToDto(Image image, byte[] fileData) {
        ImageDto dto = new ImageDto();
        dto.setId(image.getId());
        dto.setPostId(image.getPostId());
        dto.setOriginalFileName(image.getOriginalFileName());
        dto.setData(fileData);
        return dto;

    }


    public static ImageUploadResponse mapToImageUploadResponse(Image image) {
        ImageUploadResponse dto = new ImageUploadResponse();
        dto.setId(image.getId());
        dto.setPostId(image.getPostId());
        dto.setFileName(image.getOriginalFileName());
        dto.setFilePath(image.getFilePath());
        return dto;
    }


}
