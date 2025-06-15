package com.rasitesdmr.spring_boot_graphQl_monolithic.photo.service;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.request.PhotoCreateRequest;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.PageResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.PhotoDetailResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.PhotoResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.photo.Photo;

public interface PhotoService {

    void savePhoto(Photo photo);
    PhotoResponse createPhoto(PhotoCreateRequest request);
    PhotoResponse getPhotoResponseById(Long id);
    Photo getById(Long id);
    PhotoDetailResponse getPhotoDetailResponseById(Long id);
    PageResponse<PhotoDetailResponse> getAllPhotoResponseByUserId(Long userId, int page, int size);
}
