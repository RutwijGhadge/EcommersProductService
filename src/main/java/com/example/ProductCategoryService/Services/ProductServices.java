package com.example.ProductCategoryService.Services;

import com.example.ProductCategoryService.DTOs.ProductDTO;
import com.example.ProductCategoryService.Models.Category;
import com.example.ProductCategoryService.Models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductServices implements IProductServices {

    private RestTemplateBuilder restTemplateBuilder;

    public ProductServices(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public List<Product> getProducts() {//get all products
        RestTemplate restTemplate=restTemplateBuilder.build();
        ProductDTO[] productDTO=restTemplate.getForEntity("https://fakestoreapi.com/products", ProductDTO[].class).getBody();
        //conversion logic of ProductDto to Product
        List<Product>products=new ArrayList<>();
        for(ProductDTO prdto:productDTO){
            products.add(getProduct(prdto));
        }
        return products;
    }

    @Override
    public Product getProduct(long productID) {//get the particular/Specific details of the product with id
        RestTemplate restTemplate=restTemplateBuilder.build();
        ProductDTO productDTO=restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", ProductDTO.class,productID).getBody();
        //conversion logic of ProductDto to Product
        return getProduct(productDTO);
    }

    @Override
    public Product createProduct(ProductDTO productDTO) {//create / add the Product
        RestTemplate restTemplate=restTemplateBuilder.build();
        ProductDTO prdto=restTemplate.postForEntity("https://fakestoreapi.com/products",productDTO,ProductDTO.class).getBody();
        return getProduct(prdto);
    }

    @Override
    public String updateProduct() {//update a Product

        return null;
    }

    private Product getProduct(ProductDTO productDTO){
        Product product=new Product();

        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());
        Category category=new Category();
        category.setName(productDTO.getCategory());
        product.setCategory(category);
        product.setId(productDTO.getId());

        return product;
    }
}
//FakeStoreProductDto -> product conversion will happen in Service layer
//postForEntity -> forEntity returns ResponseEntity
//postForEntity.getBody()-> returns the object only
//get/postForBody -> returns the Object only