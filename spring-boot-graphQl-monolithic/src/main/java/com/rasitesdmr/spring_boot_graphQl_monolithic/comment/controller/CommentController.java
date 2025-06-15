package com.rasitesdmr.spring_boot_graphQl_monolithic.comment.controller;

import com.rasitesdmr.spring_boot_graphQl_monolithic.comment.service.CommentService;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.request.CommentCreateRequest;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.CommentResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.PageResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.PhotoDetailResponse;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @MutationMapping
    public CommentResponse createComment(@Argument(value = "commentCreateRequest") CommentCreateRequest commentCreateRequest) {
        return commentService.createComment(commentCreateRequest);
    }

    @QueryMapping
    public List<CommentResponse> getAllCommentResponseByPhotoId(@Argument(value = "photoId") Long photoId) {
        return commentService.getAllCommentResponseByPhotoId(photoId);
    }

    @SchemaMapping(typeName = "PhotoDetailResponse", field = "comments")
    public PageResponse<CommentResponse> getAllCommentResponseByPhotoId(PhotoDetailResponse photoDetailResponse,
                                                                                 @Argument int page,
                                                                                 @Argument int size) {
        return commentService.getAllCommentResponseByPhotoId(photoDetailResponse.getPhoto().getId(), page, size);
    }

}
