package com.rasitesdmr.commentservice.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class PhotoResponse {
    private Long id;
    private String imageUrl;
    private Long userId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public PhotoResponse(Long id, String imageUrl, Long userId, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
