package com.rasitesdmr.commentservice.service;

import com.rasitesdmr.commentservice.domain.request.CommentCreateRequest;
import com.rasitesdmr.commentservice.domain.response.CommentResponse;
import com.rasitesdmr.commentservice.domain.response.PageResponse;
import com.rasitesdmr.commentservice.model.Comment;

public interface CommentService {

    void saveComment(Comment comment);
    CommentResponse createComment(CommentCreateRequest request);
    CommentResponse getCommentResponseById(Long id);

    PageResponse<CommentResponse> getAllCommentResponseByPhotoId(Long photoId, int pageNo, int pageSize);
}
