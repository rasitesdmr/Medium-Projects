package com.rasitesdmr.spring_boot_graphQl_monolithic.photo.repository;

import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.PhotoResponse;
import com.rasitesdmr.spring_boot_graphQl_monolithic.photo.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("Select p From Photo p " +
            "Where p.id = :id ")
    PhotoResponse findPhotoResponseById(Long id);

    @Query("Select p From Photo p " +
            "Where p.user.id = :userId ")
    Page<PhotoResponse> findAllPhotoResponseByUserId(Long userId, Pageable pageable);
}
