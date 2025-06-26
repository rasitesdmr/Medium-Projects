package com.rasitesdmr.likeservice.domain.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class LikeResponse {
    private Long id;
    private Long userId;
    private Long photoId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public LikeResponse(Long id, Long userId, Long photoId, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.userId = userId;
        this.photoId = photoId;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
