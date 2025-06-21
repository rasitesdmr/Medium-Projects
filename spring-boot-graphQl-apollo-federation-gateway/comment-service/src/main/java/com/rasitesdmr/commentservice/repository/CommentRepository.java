package com.rasitesdmr.commentservice.repository;

import com.rasitesdmr.commentservice.domain.response.CommentResponse;
import com.rasitesdmr.commentservice.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("Select new com.rasitesdmr.commentservice.domain.response.CommentResponse(c.id, c.text,c.userId, c.photoId,c.createDate, c.updateDate) From Comment c " +
            "Where c.id = :id ")
    CommentResponse findCommentResponseById(Long id);

    @Query("Select new com.rasitesdmr.commentservice.domain.response.CommentResponse(c.id, c.text,c.userId, c.photoId,c.createDate, c.updateDate) From Comment c " +
            "Where c.photoId = :photoId ")
    Page<CommentResponse>findAllCommentResponseByPhotoId(Long photoId , Pageable pageable);
}
