package com.vodafone.iot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public
class PaginationMetadata {
    private int pageSize;
    private int pageNumber;
    private long totalElements;
    private int totalPages;
}
