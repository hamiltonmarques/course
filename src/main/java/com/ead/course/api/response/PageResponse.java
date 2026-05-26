package com.ead.course.api.response;

import com.ead.course.dtos.PageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> items;
    private PageDTO page;
}
