package com.rasitesdmr.spring_boot_graphQl_monolithic.domain.request;

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
