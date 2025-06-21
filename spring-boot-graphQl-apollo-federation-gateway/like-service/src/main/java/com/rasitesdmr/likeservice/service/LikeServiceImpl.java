package com.rasitesdmr.likeservice.service;

import com.rasitesdmr.likeservice.client.PhotoGraphQLClient;
import com.rasitesdmr.likeservice.client.UserGraphQLClient;
import com.rasitesdmr.likeservice.domain.exception.exceptions.ConflictException;
import com.rasitesdmr.likeservice.domain.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.likeservice.domain.exception.exceptions.NotFoundException;
import com.rasitesdmr.likeservice.domain.request.LikeCreateRequest;
import com.rasitesdmr.likeservice.domain.response.LikeResponse;
import com.rasitesdmr.likeservice.domain.response.PageResponse;
import com.rasitesdmr.likeservice.model.Like;
import com.rasitesdmr.likeservice.repository.LikeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    private static final Logger logger = LoggerFactory.getLogger(LikeServiceImpl.class);
    private final LikeRepository likeRepository;
    private final UserGraphQLClient userGraphQLClient;
    private final PhotoGraphQLClient photoGraphQLClient;

    public LikeServiceImpl(LikeRepository likeRepository, UserGraphQLClient userGraphQLClient, PhotoGraphQLClient photoGraphQLClient) {
        this.likeRepository = likeRepository;
        this.userGraphQLClient = userGraphQLClient;
        this.photoGraphQLClient = photoGraphQLClient;
    }


    @Override
    public void saveLike(Like like) {
        try {
            likeRepository.save(like);
            logger.info("Like registered successfully. [{}]", like.toString());
        } catch (Exception exception) {
            logger.error("Like registration failed. [{}]", exception.getMessage());
            throw new InternalServerErrorException("Like registration failed.");
        }
    }

    @Override
    public LikeResponse createLike(LikeCreateRequest request) {
        final Long requestUserId = request.getUserId();
        final Long requestPhotoId = request.getPhotoId();
        boolean userIdAndPhotoIdExist = existsByUserIdAndPhotoId(requestUserId, requestPhotoId);
        if (userIdAndPhotoIdExist) throw new ConflictException("You already liked the photo");

        boolean existUser = userGraphQLClient.existsUserByUserId(requestUserId);
        if (!existUser) throw new NotFoundException("User with ID " + requestUserId + " not found.");
        boolean existPhoto = photoGraphQLClient.existsPhotoByUserId(requestPhotoId);
        if (!existPhoto) throw new NotFoundException("Photo with ID " + requestPhotoId + " not found.");
        Like like = Like.builder()
                .userId(requestUserId)
                .photoId(requestPhotoId)
                .build();
        saveLike(like);
        return getLikeResponseById(like.getId());
    }

    @Override
    public boolean existsByUserIdAndPhotoId(Long userId, Long photoId) {
        return likeRepository.existsByUserIdAndPhotoId(userId, photoId);
    }

    @Override
    public LikeResponse getLikeResponseById(Long id) {
        return likeRepository.findLikeResponseById(id);
    }

    @Override
    public PageResponse<LikeResponse> getAllLikeResponseByPhotoId(Long photoId, int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<LikeResponse> likeResponses = likeRepository.findAllLikeResponseByPhotoId(photoId, paging);
        return new PageResponse<>(
                likeResponses.getContent(),
                likeResponses.getTotalPages(),
                likeResponses.getTotalElements(),
                likeResponses.getNumber(),
                likeResponses.getSize()
        );
    }

}
