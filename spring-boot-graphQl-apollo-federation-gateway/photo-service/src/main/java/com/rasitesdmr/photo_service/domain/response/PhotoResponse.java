package com.rasitesdmr.photo_service.domain.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PhotoResponse {
    private Long id;
    private String imageUrl;
    private Long userId;
    private Date createDate;
    private Date updateDate;

    public PhotoResponse(Long id, String imageUrl, Long userId, Date createDate, Date updateDate) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
