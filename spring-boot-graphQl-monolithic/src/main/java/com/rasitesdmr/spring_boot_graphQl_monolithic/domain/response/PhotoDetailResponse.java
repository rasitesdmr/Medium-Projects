package com.rasitesdmr.spring_boot_graphQl_monolithic.domain.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotoDetailResponse {
    private PhotoResponse photo;
}
