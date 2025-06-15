package com.rasitesdmr.spring_boot_graphQl_monolithic.photo.service;

import com.rasitesdmr.spring_boot_graphQl_monolithic.comment.service.CommentService;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.exception.exceptions.NotFoundException;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.request.PhotoCreateRequest;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.*;
import com.rasitesdmr.spring_boot_graphQl_monolithic.like.service.LikeService;
import com.rasitesdmr.spring_boot_graphQl_monolithic.photo.Photo;
import com.rasitesdmr.spring_boot_graphQl_monolithic.photo.repository.PhotoRepository;
import com.rasitesdmr.spring_boot_graphQl_monolithic.user.User;
import com.rasitesdmr.spring_boot_graphQl_monolithic.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {
    private static final Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);
    private final PhotoRepository photoRepository;
    private final UserService userService;
    private final LikeService likeService;
    private final CommentService commentService;

    public PhotoServiceImpl(PhotoRepository photoRepository, UserService userService, @Lazy LikeService likeService, @Lazy CommentService commentService) {
        this.photoRepository = photoRepository;
        this.userService = userService;
        this.likeService = likeService;
        this.commentService = commentService;
    }


    @Override
    public void savePhoto(Photo photo) {
        try {
            photoRepository.save(photo);
            logger.info("Photo registered successfully. [{}]", photo.toString());
        } catch (Exception exception) {
            logger.error("Photo registration failed. [{}]", exception.getMessage());
            throw new InternalServerErrorException("Photo registration failed.");
        }
    }

    @Override
    public PhotoResponse createPhoto(PhotoCreateRequest request) {
        final Long requestUserId = request.getUserId();
        final String requestImageUrl = request.getImageUrl();
        final User user = userService.getById(requestUserId);
        Photo photo = Photo.builder()
                .imageUrl(requestImageUrl)
                .user(user)
                .build();
        savePhoto(photo);
        return getPhotoResponseById(photo.getId());
    }

    @Override
    public PhotoResponse getPhotoResponseById(Long id) {
        return photoRepository.findPhotoResponseById(id);
    }

    @Override
    public Photo getById(Long id) {
        return photoRepository.findById(id).orElseThrow(() -> new NotFoundException("Photo not found"));
    }

    @Override
    public PhotoDetailResponse getPhotoDetailResponseById(Long id) {
        PhotoResponse photoResponse = getPhotoResponseById(id);
        return PhotoDetailResponse.builder()
                .photo(photoResponse)
                .build();
    }

    @Override
    public PageResponse<PhotoDetailResponse> getAllPhotoResponseByUserId(Long userId, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<PhotoResponse> photoResponses = photoRepository.findAllPhotoResponseByUserId(userId, paging);

        List<PhotoDetailResponse> detailContent = photoResponses.getContent().stream()
                .map(photo -> PhotoDetailResponse.builder()
                        .photo(photo)
                        .build())
                .toList();

        return new PageResponse<>(
                detailContent,
                photoResponses.getTotalPages(),
                photoResponses.getTotalElements(),
                photoResponses.getNumber(),
                photoResponses.getSize()
        );
    }
}
