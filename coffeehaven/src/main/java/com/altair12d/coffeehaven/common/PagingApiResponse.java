package com.altair12d.coffeehaven.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class PagingApiResponse<T> {
    private String message;
    private T data;
    @Builder.Default
    private int statusCode = 1000;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}
