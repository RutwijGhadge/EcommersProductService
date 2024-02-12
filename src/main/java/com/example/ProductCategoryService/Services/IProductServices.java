package com.example.ProductCategoryService.Services;

import com.example.ProductCategoryService.DTOs.ProductDTO;
import com.example.ProductCategoryService.Models.Product;


import java.util.List;

public interface IProductServices {
    List<Product>  getProducts();

    Product getProduct(long id);

    Product createProduct(ProductDTO productDTO);

    String updateProduct();
}
