package com.example.ProductCategoryService.Repository;

import com.example.ProductCategoryService.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Long> {

}
