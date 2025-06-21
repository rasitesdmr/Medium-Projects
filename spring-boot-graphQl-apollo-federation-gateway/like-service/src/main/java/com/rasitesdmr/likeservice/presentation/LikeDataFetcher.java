package com.rasitesdmr.likeservice.presentation;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.rasitesdmr.likeservice.domain.request.LikeCreateRequest;
import com.rasitesdmr.likeservice.domain.response.LikeResponse;
import com.rasitesdmr.likeservice.service.LikeService;

@DgsComponent
public class LikeDataFetcher {

    private final LikeService likeService;


    public LikeDataFetcher(LikeService likeService) {
        this.likeService = likeService;
    }

    @DgsMutation
    public LikeResponse createLike(@InputArgument(value = "likeCreateRequest") LikeCreateRequest likeCreateRequest) {
        return likeService.createLike(likeCreateRequest);
    }

    @DgsQuery
    public LikeResponse getLikeResponseById(@InputArgument(value = "id") Long id) {
        return likeService.getLikeResponseById(id);
    }
}
