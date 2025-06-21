package com.rasitesdmr.likeservice.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeCreateRequest {

    private Long userId;
    private Long photoId;
}
