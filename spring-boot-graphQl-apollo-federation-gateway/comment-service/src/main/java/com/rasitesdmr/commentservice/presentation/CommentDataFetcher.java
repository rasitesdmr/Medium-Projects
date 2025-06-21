package com.rasitesdmr.commentservice.presentation;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.rasitesdmr.commentservice.domain.request.CommentCreateRequest;
import com.rasitesdmr.commentservice.domain.response.CommentResponse;
import com.rasitesdmr.commentservice.service.CommentService;

@DgsComponent
public class CommentDataFetcher {

    private final CommentService commentService;

    public CommentDataFetcher(CommentService commentService) {
        this.commentService = commentService;
    }

    @DgsMutation
    public CommentResponse createComment(@InputArgument(value = "commentCreateRequest") CommentCreateRequest commentCreateRequest) {
        return commentService.createComment(commentCreateRequest);
    }

    @DgsQuery
    public CommentResponse getCommentResponseById(@InputArgument(value = "id") Long id) {
        return commentService.getCommentResponseById(id);
    }
}
