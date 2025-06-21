package com.rasitesdmr.likeservice.repository;

import com.rasitesdmr.likeservice.domain.response.LikeResponse;
import com.rasitesdmr.likeservice.model.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndPhotoId(Long userId, Long photoId);

    @Query("select new com.rasitesdmr.likeservice.domain.response.LikeResponse(l.id,l.userId, l.photoId, l.createDate, l.updateDate) from  Like l " +
            "where l.id = :id")
    LikeResponse findLikeResponseById(Long id);

    @Query("select new com.rasitesdmr.likeservice.domain.response.LikeResponse(l.id, l.userId, l.photoId, l.createDate, l.updateDate) from Like l " +
            "where l.photoId = :photoId ")
    Page<LikeResponse> findAllLikeResponseByPhotoId(Long photoId , Pageable pageable);
}
