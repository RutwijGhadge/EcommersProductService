package com.example.ProductCategoryService.Services;


import com.example.ProductCategoryService.Models.Product;


import java.util.List;

public interface IProductServices {
    List<Product>  getProducts();

    Product getProduct(long id);

    Product createProduct(Product product);

    Product updateProduct(Long id, Product product);
}
