package com.rasitesdmr.photo_service.repository;

import com.rasitesdmr.photo_service.domain.response.PageResponse;
import com.rasitesdmr.photo_service.domain.response.PhotoResponse;
import com.rasitesdmr.photo_service.model.Photo;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("Select new com.rasitesdmr.photo_service.domain.response.PhotoResponse(p.id, p.imageUrl, p.userId, p.createDate, p.updateDate) From Photo p " +
            "Where p.id = :id ")
    PhotoResponse findPhotoResponseById(Long id);

    @Query("select new com.rasitesdmr.photo_service.domain.response.PhotoResponse(p.id, p.imageUrl, p.userId, p.createDate, p.updateDate) from Photo p " +
            "where p.userId = :userId")
    Page<PhotoResponse> findAllByPhotoResponseByUserId(Long userId ,Pageable pageable);

    boolean existsById(@NotNull Long id);
}
