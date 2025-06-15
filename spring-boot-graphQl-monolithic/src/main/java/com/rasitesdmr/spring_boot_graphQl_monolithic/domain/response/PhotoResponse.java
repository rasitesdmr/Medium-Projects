package com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response;

import java.time.LocalDateTime;

public interface PhotoResponse {
    Long getId();
    String getImageUrl();
    LocalDateTime getCreateDate();
    LocalDateTime getUpdateDate();
}
