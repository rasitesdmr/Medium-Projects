package com.rasitesdmr.spring_boot_graphQl_monolithic.comment.service;

import com.rasitesdmr.spring_boot_graphQl_monolithic.comment.Comment;
import com.rasitesdmr.spring_boot_graphQl_monolithic.comment.repository.CommentRepository;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.request.CommentCreateRequest;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.CommentResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.PageResponse;
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
public class CommentServiceImpl implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PhotoService photoService;

    public CommentServiceImpl(CommentRepository commentRepository, UserService userService, PhotoService photoService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.photoService = photoService;
    }

    @Override
    public void saveComment(Comment comment) {
        try {
            commentRepository.save(comment);
            logger.info("Comment registered successfully. [{}]", comment.toString());
        } catch (Exception exception) {
            logger.error("Comment registration failed. [{}]", exception.getMessage());
            throw new InternalServerErrorException("Comment registration failed.");
        }
    }

    @Override
    public CommentResponse createComment(CommentCreateRequest request) {
        final String requestText = request.getText();
        final Long requestUserId = request.getUserId();
        final Long requestPhotoId = request.getPhotoId();
        final User user = userService.getById(requestUserId);
        final Photo photo = photoService.getById(requestPhotoId);

        Comment comment = Comment.builder()
                .text(requestText)
                .user(user)
                .photo(photo)
                .build();

        saveComment(comment);
        return getCommentResponseById(comment.getId());
    }

    @Override
    public CommentResponse getCommentResponseById(Long id) {
        return commentRepository.findCommentResponseById(id);
    }

    @Override
    public List<CommentResponse> getAllCommentResponseByPhotoId(Long photoId) {
        return commentRepository.findAllCommentResponseByPhotoId(photoId);
    }

    @Override
    public PageResponse<CommentResponse> getAllCommentResponseByPhotoId(Long photoId, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<CommentResponse> commentResponses = commentRepository.findAllCommentResponseByPhotoId(photoId, paging);
        return new PageResponse<>(
                commentResponses.getContent(),
                commentResponses.getTotalPages(),
                commentResponses.getTotalElements(),
                commentResponses.getNumber(),
                commentResponses.getSize()
        );
    }
}
