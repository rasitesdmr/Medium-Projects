package com.rasitesdmr.likeservice.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int number;
    private int size;
}
