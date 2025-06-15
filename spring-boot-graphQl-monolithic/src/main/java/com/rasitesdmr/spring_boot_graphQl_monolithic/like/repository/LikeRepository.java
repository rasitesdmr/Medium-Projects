package com.rasitesdmr.spring_boot_graphQl_monolithic.like.repository;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.LikeResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.like.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {

    @Query("Select l From Like l " +
            "Where l.id = :id ")
    LikeResponse findLikeResponseById(Long id);

    boolean existsByUserIdAndPhotoId(Long userId, Long photoId);

    @Query("Select l From Like l " +
            "Where l.user.id = :userId ")
    List<LikeResponse> findAllLikeResponseByUserId(Long userId);

    @Query("Select l From Like l " +
            "Where l.photo.id = :photoId ")
    List<LikeResponse> findAllLikeResponseByPhotoId(Long photoId);

    @Query("Select l From Like l " +
            "Where l.photo.id = :photoId ")
    Page<LikeResponse> findAllLikeResponseByPhotoId(Long photoId, Pageable pageable);
}
