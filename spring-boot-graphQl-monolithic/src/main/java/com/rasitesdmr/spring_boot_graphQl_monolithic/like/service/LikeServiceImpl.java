package com.rasitesdmr.spring_boot_graphQl_monolithic.like.service;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.exception.exceptions.ConflictException;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.request.LikeCreateRequest;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.LikeResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.PageResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.like.Like;
import com.rasitesdmr.spring_boot_graphQl_monolithic.like.repository.LikeRepository;
import com.rasitesdmr.spring_boot_graphQl_monolithic.photo.Photo;
import com.rasitesdmr.spring_boot_graphQl_monolithic.photo.service.PhotoService;
import com.rasitesdmr.spring_boot_graphQl_monolithic.user.User;
import com.rasitesdmr.spring_boot_graphQl_monolithic.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {
    private static final Logger logger = LoggerFactory.getLogger(LikeServiceImpl.class);
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PhotoService photoService;

    public LikeServiceImpl(LikeRepository likeRepository, UserService userService, PhotoService photoService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.photoService = photoService;
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
        final User user = userService.getById(requestUserId);
        final Photo photo = photoService.getById(requestPhotoId);
        Like like = Like.builder()
                .user(user)
                .photo(photo)
                .build();
        saveLike(like);
        return getLikeResponseById(like.getId());
    }

    @Override
    public LikeResponse getLikeResponseById(Long id) {
        return likeRepository.findLikeResponseById(id);
    }

    @Override
    public boolean existsByUserIdAndPhotoId(Long userId, Long photoId) {
        return likeRepository.existsByUserIdAndPhotoId(userId, photoId);
    }

    @Override
    public List<LikeResponse> getAllLikeResponseByUserId(Long userId) {
        return likeRepository.findAllLikeResponseByUserId(userId);
    }

    @Override
    public List<LikeResponse> getAllLikeResponseByPhotoId(Long photoId) {
        return likeRepository.findAllLikeResponseByPhotoId(photoId);
    }

    @Override
    public PageResponse<LikeResponse> getAllLikeResponseByPhotoId(Long photoId, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
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
