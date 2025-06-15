package com.rasitesdmr.spring_boot_graphQl_monolithic.like.service;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.request.LikeCreateRequest;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.LikeResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.PageResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.like.Like;

import java.util.List;

public interface LikeService {

    void saveLike(Like like);
    LikeResponse createLike(LikeCreateRequest request);
    LikeResponse getLikeResponseById(Long id);
    boolean existsByUserIdAndPhotoId(Long userId, Long photoId);
    List<LikeResponse> getAllLikeResponseByUserId(Long userId);
    List<LikeResponse> getAllLikeResponseByPhotoId(Long photoId);
    PageResponse<LikeResponse> getAllLikeResponseByPhotoId(Long photoId, int page, int size);
}
