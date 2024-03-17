package com.example.ProductCategoryService.Services;

import com.example.ProductCategoryService.Models.Product;
import com.example.ProductCategoryService.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StorageProductService implements IProductServices{
    //this class will interact with ProductRepo
    ProductRepo productRepo;

    public StorageProductService(ProductRepo productRepo){
        this.productRepo=productRepo;
    }
    @Override
    public List<Product> getProducts() {
        return null;
    }

    @Override
    public Product getProduct(long id) {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
      return productRepo.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public String deleteProduct(Long id) {
       Optional<Product>optionalProduct=productRepo.findById(id);
       if(!optionalProduct.isEmpty()){
           productRepo.deleteById(id);
           return "Deleted Successfully";
       }
       return "product not present";
    }

    @Override
    public void putProduct(Long id, Product product) {
        createProduct(product);

    }
}
