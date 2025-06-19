package com.rasitesdmr.photo_service.presentation;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.rasitesdmr.photo_service.domain.request.PhotoCreateRequest;
import com.rasitesdmr.photo_service.domain.response.PhotoResponse;
import com.rasitesdmr.photo_service.service.PhotoService;

@DgsComponent
public class PhotoDataFetcher {

    private final PhotoService photoService;

    public PhotoDataFetcher(PhotoService photoService) {
        this.photoService = photoService;
    }

    @DgsMutation
    public PhotoResponse createPhoto(@InputArgument(value = "photoCreateRequest") PhotoCreateRequest photoCreateRequest) {
        return photoService.createPhoto(photoCreateRequest);
    }

    @DgsQuery
    public PhotoResponse getPhotoResponseById(@InputArgument(value = "id") Long id) {
        return photoService.getPhotoResponseById(id);
    }
}
