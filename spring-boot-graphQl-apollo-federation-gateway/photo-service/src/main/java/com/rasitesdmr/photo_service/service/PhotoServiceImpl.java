package com.rasitesdmr.photo_service.service;

import com.rasitesdmr.photo_service.client.UserGraphQLClient;
import com.rasitesdmr.photo_service.domain.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.photo_service.domain.exception.exceptions.NotFoundException;
import com.rasitesdmr.photo_service.domain.request.PhotoCreateRequest;
import com.rasitesdmr.photo_service.domain.response.*;
import com.rasitesdmr.photo_service.model.Photo;
import com.rasitesdmr.photo_service.repository.PhotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {
    private static final Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);
    private final PhotoRepository photoRepository;
    private final UserGraphQLClient userGraphQLClient;

    public PhotoServiceImpl(PhotoRepository photoRepository, UserGraphQLClient userGraphQLClient) {
        this.photoRepository = photoRepository;
        this.userGraphQLClient = userGraphQLClient;
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

        boolean existUser = userGraphQLClient.existsUserByUserId(requestUserId);
        if (!existUser) throw new NotFoundException("User with ID " + requestUserId + " not found.");

        Photo photo = Photo.builder()
                .imageUrl(requestImageUrl)
                .userId(requestUserId)
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
    public PageResponse<PhotoResponse> getAllByPhotoResponseByUserId(Long userId, int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<PhotoResponse>photoResponses = photoRepository.findAllByPhotoResponseByUserId(userId,paging);
        return new PageResponse<>(
                photoResponses.getContent(),
                photoResponses.getTotalPages(),
                photoResponses.getTotalElements(),
                photoResponses.getNumber(),
                photoResponses.getSize()
        );
    }
}
