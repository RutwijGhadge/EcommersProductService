package com.example.ProductCategoryService.Controller;

import com.example.ProductCategoryService.DTOs.ProductDTO;
import com.example.ProductCategoryService.Models.Category;
import com.example.ProductCategoryService.Models.Product;
import com.example.ProductCategoryService.Services.IProductServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {
        IProductServices productServices;

        public ProductController(IProductServices productServices) {
            this.productServices = productServices;
        }

        @GetMapping("")
        public ResponseEntity<List<Product>> getProducts(){
            try{
                List<Product>products=new ArrayList<>();
                products= productServices.getProducts();
                return new ResponseEntity<>(products, HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<Product> getProducts(@PathVariable(value="id") long ids){
            try{
                if(ids<1)
                    throw new IllegalArgumentException("Product Id is <1");
                Product product= productServices.getProduct(ids);
                return new ResponseEntity<>(product, HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }

        @PostMapping("")
        public Product createProduct(@RequestBody ProductDTO productDTO){
            Product product=getProductFromDto((productDTO));
            return productServices.createProduct(product);
        }

        @PatchMapping("")
        public Product updateProduct(@PathVariable(value = "id")Long id,@RequestBody Product product){
            return productServices.updateProduct(id,product);
        }


        private Product getProductFromDto(ProductDTO productDTO){
            Product product=new Product();
            product.setTitle(productDTO.getTitle());
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());
            product.setImage(productDTO.getImage());
            Category category=new Category();
            category.setName(productDTO.getCategory());
            product.setCategory(category);
            return product;
        }
}


//productDTO -> product conversion in controller Layer
