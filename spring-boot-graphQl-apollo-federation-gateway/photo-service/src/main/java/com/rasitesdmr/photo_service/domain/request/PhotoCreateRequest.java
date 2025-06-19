package com.rasitesdmr.photo_service.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoCreateRequest {
    private Long userId;
    private String imageUrl;
}
