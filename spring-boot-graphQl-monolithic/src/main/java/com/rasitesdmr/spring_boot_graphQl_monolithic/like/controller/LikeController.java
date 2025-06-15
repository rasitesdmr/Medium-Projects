package com.rasitesdmr.spring_boot_graphQl_monolithic.like.controller;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.request.LikeCreateRequest;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.*;
import com.rasitesdmr.spring_boot_graphQl_monolithic.like.service.LikeService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @MutationMapping
    public LikeResponse createLike(@Argument(value = "likeCreateRequest") LikeCreateRequest likeCreateRequest) {
        return likeService.createLike(likeCreateRequest);
    }

    @QueryMapping
    public List<LikeResponse> getAllLikeResponseByUserId(@Argument(value = "userId") Long userId) {
        return likeService.getAllLikeResponseByUserId(userId);
    }

    @SchemaMapping(typeName = "PhotoDetailResponse", field = "likes")
    public PageResponse<LikeResponse> getAllLikeResponseByPhotoId(PhotoDetailResponse photoDetailResponse,
                                                                  @Argument int page,
                                                                  @Argument int size) {
        return likeService.getAllLikeResponseByPhotoId(photoDetailResponse.getPhoto().getId(), page, size);
    }

}
