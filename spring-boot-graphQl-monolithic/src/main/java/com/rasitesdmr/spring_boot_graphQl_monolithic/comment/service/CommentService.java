package com.rasitesdmr.spring_boot_graphQl_monolithic.comment.service;

import com.rasitesdmr.spring_boot_graphQl_monolithic.comment.Comment;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.request.CommentCreateRequest;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.CommentResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.PageResponse;

import java.util.List;

public interface CommentService {

    void saveComment(Comment comment);
    CommentResponse createComment(CommentCreateRequest request);
    CommentResponse getCommentResponseById(Long id);
    List<CommentResponse> getAllCommentResponseByPhotoId(Long photoId);
    PageResponse<CommentResponse> getAllCommentResponseByPhotoId(Long photoId, int page, int size);
}
