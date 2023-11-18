package com.example.pdp_student_managment_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<T> {
    private boolean isSorted;
    private Integer pageNumber;
    private Integer size;
    private List<T> content;
    private Long totalElements;
    private Integer totalPages;
    private boolean last;
    private boolean first;
    private Long offset;
}
