package com.rasitesdmr.photo_service.domain.response;

import java.time.LocalDateTime;

public interface PhotoResponse {
    Long getId();
    String getImageUrl();
    Long getUserId();
    LocalDateTime getCreateDate();
    LocalDateTime getUpdateDate();
}
