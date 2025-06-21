package com.rasitesdmr.likeservice.service;

import com.rasitesdmr.likeservice.domain.request.LikeCreateRequest;
import com.rasitesdmr.likeservice.domain.response.LikeResponse;
import com.rasitesdmr.likeservice.domain.response.PageResponse;
import com.rasitesdmr.likeservice.model.Like;

public interface LikeService {

    void saveLike(Like like);
    LikeResponse createLike(LikeCreateRequest request);
    boolean existsByUserIdAndPhotoId(Long userId, Long photoId);
    LikeResponse getLikeResponseById(Long id);
    PageResponse<LikeResponse> getAllLikeResponseByPhotoId(Long photoId, int pageNo, int pageSize);
}
