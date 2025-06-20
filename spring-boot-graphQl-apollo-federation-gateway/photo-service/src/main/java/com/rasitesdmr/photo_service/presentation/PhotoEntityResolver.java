package com.rasitesdmr.photo_service.presentation;

import com.netflix.graphql.dgs.*;
import com.rasitesdmr.photo_service.domain.response.PageResponse;
import com.rasitesdmr.photo_service.domain.response.PhotoResponse;
import com.rasitesdmr.photo_service.domain.response.UserResponse;
import com.rasitesdmr.photo_service.service.PhotoService;

import java.util.Map;

@DgsComponent
public class PhotoEntityResolver {

    private final PhotoService photoService;

    public PhotoEntityResolver(PhotoService photoService) {
        this.photoService = photoService;
    }

    @DgsEntityFetcher(name = "UserResponse")
    public UserResponse resolveUserResponse(Map<String, Object> values) {
        Long userId = Long.valueOf(values.get("id").toString());
        return UserResponse.builder()
                .id(userId)
                .build();
    }

    @DgsData(parentType = "UserResponse", field = "photos")
    PageResponse<PhotoResponse> getPhotos(DgsDataFetchingEnvironment dfe, @InputArgument Integer pageNo, @InputArgument Integer pageSize){
        UserResponse userResponse = dfe.getSource();
        Long userId = userResponse.getId();
        return photoService.getAllByPhotoResponseByUserId(userId, pageNo, pageSize);
    }
}
