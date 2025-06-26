package com.rasitesdmr.commentservice.domain.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String text;
    private Long userId;
    private Long photoId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public CommentResponse(Long id, String text, Long userId, Long photoId, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.text = text;
        this.userId = userId;
        this.photoId = photoId;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
