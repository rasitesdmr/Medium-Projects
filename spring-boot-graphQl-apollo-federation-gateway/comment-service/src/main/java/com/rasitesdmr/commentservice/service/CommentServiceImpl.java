package com.rasitesdmr.commentservice.service;

import com.rasitesdmr.commentservice.client.PhotoGraphQLClient;
import com.rasitesdmr.commentservice.client.UserGraphQLClient;
import com.rasitesdmr.commentservice.domain.exception.exceptions.InternalServerErrorException;
import com.rasitesdmr.commentservice.domain.exception.exceptions.NotFoundException;
import com.rasitesdmr.commentservice.domain.request.CommentCreateRequest;
import com.rasitesdmr.commentservice.domain.response.CommentResponse;
import com.rasitesdmr.commentservice.domain.response.PageResponse;
import com.rasitesdmr.commentservice.model.Comment;
import com.rasitesdmr.commentservice.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;
    private final UserGraphQLClient userGraphQLClient;
    private final PhotoGraphQLClient photoGraphQLClient;

    public CommentServiceImpl(CommentRepository commentRepository, UserGraphQLClient userGraphQLClient, PhotoGraphQLClient photoGraphQLClient) {
        this.commentRepository = commentRepository;
        this.userGraphQLClient = userGraphQLClient;
        this.photoGraphQLClient = photoGraphQLClient;
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

        boolean existUser = userGraphQLClient.existsUserByUserId(requestUserId);
        if (!existUser) throw new NotFoundException("User with ID " + requestUserId + " not found.");
        boolean existPhoto = photoGraphQLClient.existsPhotoByUserId(requestPhotoId);
        if (!existPhoto) throw new NotFoundException("Photo with ID " + requestPhotoId + " not found.");

        Comment comment = Comment.builder()
                .text(requestText)
                .userId(requestUserId)
                .photoId(requestPhotoId)
                .build();

        saveComment(comment);
        return getCommentResponseById(comment.getId());
    }

    @Override
    public CommentResponse getCommentResponseById(Long id) {
        return commentRepository.findCommentResponseById(id);
    }

    @Override
    public PageResponse<CommentResponse> getAllCommentResponseByPhotoId(Long photoId, int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
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
