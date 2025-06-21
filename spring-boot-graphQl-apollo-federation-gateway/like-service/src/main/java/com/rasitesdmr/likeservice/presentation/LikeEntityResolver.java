package com.rasitesdmr.likeservice.presentation;

import com.netflix.graphql.dgs.*;
import com.rasitesdmr.likeservice.domain.response.LikeResponse;
import com.rasitesdmr.likeservice.domain.response.PageResponse;
import com.rasitesdmr.likeservice.domain.response.PhotoResponse;
import com.rasitesdmr.likeservice.service.LikeService;

import java.util.Map;

@DgsComponent
public class LikeEntityResolver {

    private final LikeService likeService;

    public LikeEntityResolver(LikeService likeService) {
        this.likeService = likeService;
    }


    @DgsEntityFetcher(name = "PhotoResponse")
    public PhotoResponse resolvePhotoResponse(Map<String, Object> values) {
        Long userId = Long.valueOf(values.get("id").toString());
        return PhotoResponse.builder()
                .id(userId)
                .build();
    }

    @DgsData(parentType = "PhotoResponse", field = "likes")
    PageResponse<LikeResponse> getLikes(DgsDataFetchingEnvironment dfe, @InputArgument Integer pageNo, @InputArgument Integer pageSize){
        PhotoResponse photoResponse = dfe.getSource();
        Long photoId = photoResponse.getId();
        return likeService.getAllLikeResponseByPhotoId(photoId, pageNo, pageSize);
    }
}
