package com.rasitesdmr.commentservice.presentation;

import com.netflix.graphql.dgs.*;
import com.rasitesdmr.commentservice.domain.response.CommentResponse;
import com.rasitesdmr.commentservice.domain.response.PageResponse;
import com.rasitesdmr.commentservice.domain.response.PhotoResponse;
import com.rasitesdmr.commentservice.service.CommentService;


import java.util.Map;

@DgsComponent
public class CommentEntityResolver {

    private final CommentService commentService;

    public CommentEntityResolver(CommentService commentService) {
        this.commentService = commentService;
    }


    @DgsEntityFetcher(name = "PhotoResponse")
    public PhotoResponse resolvePhotoResponse(Map<String, Object> values) {
        Long userId = Long.valueOf(values.get("id").toString());
        return PhotoResponse.builder()
                .id(userId)
                .build();
    }

    @DgsData(parentType = "PhotoResponse", field = "comments")
    PageResponse<CommentResponse> getComments(DgsDataFetchingEnvironment dfe, @InputArgument Integer pageNo, @InputArgument Integer pageSize) {
        PhotoResponse photoResponse = dfe.getSource();
        Long photoId = photoResponse.getId();
        return commentService.getAllCommentResponseByPhotoId(photoId, pageNo, pageSize);
    }
}
