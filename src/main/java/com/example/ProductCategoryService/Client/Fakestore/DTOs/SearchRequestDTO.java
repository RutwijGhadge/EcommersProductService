package com.example.ProductCategoryService.Client.Fakestore.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDTO {
    private String query;
    private int pageSize;
    private int pageNumber;
}
//pageNumber will start from 0