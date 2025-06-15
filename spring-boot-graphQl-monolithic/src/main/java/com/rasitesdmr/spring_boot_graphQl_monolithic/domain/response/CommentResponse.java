package com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response;

import java.time.LocalDateTime;

public interface CommentResponse {
    Long getId();
    String getText();
    LocalDateTime getCreateDate();
    LocalDateTime getUpdateDate();
}
