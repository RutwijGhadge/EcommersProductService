package com.example.ProductCategoryService.Controller;

import com.example.ProductCategoryService.Models.Product;
import com.example.ProductCategoryService.Services.IProductServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

   // @Autowired
    @MockBean
    private IProductServices productServices;//3rd party dependency -> we use Mock

    @Test
    @DisplayName("GetProductReturnProduct")
    public void Test_GetProduct_ReturnProduct(){
        Product product=new Product();
        product.setPrice(1000);
        product.setTitle("Iphone");

        //Arrange
        when(productServices.getProduct(any(Long.class))).thenReturn(product);
        //Act
        ResponseEntity<Product>products =productController.getProducts(1L);
        //Assert
        assertNotNull(products);
        assertEquals(1000,products.getBody().getPrice());
        assertEquals("Iphone",products.getBody().getTitle());
    }

    @Test
    @DisplayName("DependencyThrowsException")
    public void Test_getProduct_InternalDependencyThrowsException(){
        //Arrange
        when(productServices.getProduct(any(Long.class))).thenThrow(new RuntimeException("Something went very Long"));
        //Act + Assert
        assertThrows(RuntimeException.class,()->productController.getProducts(1L));

    }

    @Test
    @DisplayName("InvalidIdException")
    public void Test_getProduct_InvalidId_ThrowsException(){
        //No need of Arrange
        assertThrows(Exception.class,()->productController.getProducts(0L));
    }

}