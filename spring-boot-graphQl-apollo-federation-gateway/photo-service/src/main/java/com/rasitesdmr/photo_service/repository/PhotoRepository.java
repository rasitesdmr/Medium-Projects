package com.rasitesdmr.photo_service.repository;

import com.rasitesdmr.photo_service.domain.response.PhotoResponse;
import com.rasitesdmr.photo_service.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("Select p From Photo p " +
            "Where p.id = :id ")
    PhotoResponse findPhotoResponseById(Long id);
}
