package com.rasitesdmr.spring_boot_graphQl_monolithic.photo.controller;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.request.PhotoCreateRequest;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.*;
import com.rasitesdmr.spring_boot_graphQl_monolithic.photo.service.PhotoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @MutationMapping
    public PhotoResponse createPhoto(@Argument(value = "photoCreateRequest") PhotoCreateRequest photoCreateRequest) {
        return photoService.createPhoto(photoCreateRequest);
    }

    @QueryMapping
    public PhotoDetailResponse getPhotoDetailResponseById(@Argument(value = "id") Long id) {
        return photoService.getPhotoDetailResponseById(id);
    }

    @SchemaMapping(typeName = "UserDetailsResponse", field = "photos")
    public PageResponse<PhotoDetailResponse> getAllPhotoResponseByUserId(UserDetailsResponse userDetailsResponse,
                                                                        @Argument int page,
                                                                        @Argument int size) {
        return photoService.getAllPhotoResponseByUserId(userDetailsResponse.getUserResponse().getId(), page, size);
    }

}
