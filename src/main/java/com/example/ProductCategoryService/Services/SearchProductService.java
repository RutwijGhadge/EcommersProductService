package com.example.ProductCategoryService.Services;

import com.example.ProductCategoryService.Models.Product;
import com.example.ProductCategoryService.Repository.ProductRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SearchProductService {
    private ProductRepo productRepo;

    public SearchProductService(ProductRepo productRepo){
        this.productRepo=productRepo;
    }

    public Page<Product> searchProduct(String query, int pageNumber, int pageSize){
        Sort sort=Sort.by("price").descending();
       return productRepo.findByTitleEquals(query, PageRequest.of(pageNumber,pageSize,sort));
    }

}
