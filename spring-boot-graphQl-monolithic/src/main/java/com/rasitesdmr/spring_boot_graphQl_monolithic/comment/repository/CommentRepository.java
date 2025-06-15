package com.rasitesdmr.spring_boot_graphQl_monolithic.comment.repository;

import com.rasitesdmr.spring_boot_graphQl_monolithic.comment.Comment;
import com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response.CommentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("Select c From Comment c " +
            "Where c.id = :id ")
    CommentResponse findCommentResponseById(Long id);

    @Query("Select c From Comment c " +
            "Where c.photo.id = :photoId ")
    Page<CommentResponse> findAllCommentResponseByPhotoId(Long photoId, Pageable pageable);


    @Query("Select c From Comment c " +
            "Where c.photo.id = :photoId " +
            "ORDER BY c.createDate DESC ")
    List<CommentResponse> findAllCommentResponseByPhotoId(Long photoId);
}
