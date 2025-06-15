package com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response;

import java.time.LocalDateTime;

public interface LikeResponse {
    Long getId();
    LocalDateTime getCreateDate();
    LocalDateTime getUpdateDate();
}
