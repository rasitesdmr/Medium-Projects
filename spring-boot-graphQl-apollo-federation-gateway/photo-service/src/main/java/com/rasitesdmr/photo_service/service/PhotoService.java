package com.rasitesdmr.photo_service.service;

import com.rasitesdmr.photo_service.domain.request.PhotoCreateRequest;
import com.rasitesdmr.photo_service.domain.response.PageResponse;
import com.rasitesdmr.photo_service.domain.response.PhotoResponse;
import com.rasitesdmr.photo_service.model.Photo;

public interface PhotoService {

    void savePhoto(Photo photo);

    PhotoResponse createPhoto(PhotoCreateRequest request);

    PhotoResponse getPhotoResponseById(Long id);

    Photo getById(Long id);

    PageResponse<PhotoResponse> getAllByPhotoResponseByUserId(Long userId, int pageNo, int pageSize);

    Boolean existsById(Long id);
}
