package com.example.ProductCategoryService.Services;

import com.example.ProductCategoryService.Client.Fakestore.Client.FakeStoreApiClient;
import com.example.ProductCategoryService.Client.Fakestore.DTOs.FakeStoreProductDTO;
import com.example.ProductCategoryService.DTOs.ProductDTO;
import com.example.ProductCategoryService.Models.Category;
import com.example.ProductCategoryService.Models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;


//@Service
public class FakeStoreProductServices implements IProductServices {

    private RestTemplateBuilder restTemplateBuilder;
    private FakeStoreApiClient fakeStoreApiClient;

    public FakeStoreProductServices(RestTemplateBuilder restTemplateBuilder,FakeStoreApiClient fakeStoreApiClient) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.fakeStoreApiClient=fakeStoreApiClient;
    }

    @Override
    public List<Product> getProducts() {//get all products
        RestTemplate restTemplate=restTemplateBuilder.build();
        FakeStoreProductDTO[] fakeStoreProductDTOS=restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDTO[].class).getBody();
        //conversion logic of ProductDto to Product
        List<Product>products=new ArrayList<>();
        for(FakeStoreProductDTO prdto:fakeStoreProductDTOS){
            products.add(getProduct(prdto));
        }
        return products;
    }

    @Override
    public void putProduct(Long id, Product product) {
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product>requestEntity=new HttpEntity<>(product,httpHeaders);
        restTemplate.exchange("https://fakestoreapi.com/products/{id}",HttpMethod.PUT,requestEntity,Product.class,id);

        /* RestTemplate restTemplate=restTemplateBuilder.build();
        restTemplate.put("https://fakestoreapi.com/products/{id}",Product.class,id);
        */
    }


    @Override
    public Product getProduct(long productID) {//get the particular/Specific details of the product with id
       // RestTemplate restTemplate=restTemplateBuilder.build();
       // FakeStoreProductDTO fakeStoreProductDTO=restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDTO.class,productID).getBody();

        //conversion logic of ProductDto to Product
        FakeStoreProductDTO fakeStoreProductDTO=fakeStoreApiClient.getProduct(productID);
        return getProduct(fakeStoreProductDTO);
    }

    @Override
    public String deleteProduct(Long id) {
            return null;
    }


    @Override
    public Product createProduct(Product product) {//create / add the Product
      //  RestTemplate restTemplate=restTemplateBuilder.build();
      //  FakeStoreProductDTO fakeStoreProductDTO=restTemplate.postForEntity("https://fakestoreapi.com/products",product,FakeStoreProductDTO.class).getBody();
        FakeStoreProductDTO fakeStoreProductDTO=getFakeStoreProductDTOFromProduct(product);
        return getProduct(fakeStoreApiClient.createProduct(fakeStoreProductDTO));
    }
    //Own Implementation of RestAPI
    private  <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod,String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
      /*  RestTemplate restTemplate=restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class
        ).build();*/
        RestTemplate restTemplate=restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url,httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    @Override
    public Product updateProduct(Long id, Product product) {//update a Product
        RestTemplate restTemplate=restTemplateBuilder.build();
      // FakeStoreProductDTO fakeStoreProductDTO=restTemplate.patchForObject("https://fakestoreapi.com/products/{id}", product , FakeStoreProductDTO.class,id);


       ResponseEntity<FakeStoreProductDTO> fakeStoreProductDTO=requestForEntity(HttpMethod.PATCH,"https://fakestoreapi.com/products/{id}",product,FakeStoreProductDTO.class,id);
      // FakeStoreProductDTO fakeStoreProductDTO2=getFakeStoreProductDTOFromProduct(product);
       //FakeStoreProductDTO fakeStoreProductDTO1=fakeStoreApiClient.updateProduct(fakeStoreProductDTO);

        Product resultantProduct=getProduct(fakeStoreProductDTO.getBody());
        return resultantProduct;
    }


    private Product getProduct(FakeStoreProductDTO productDTO){
        Product product=new Product();
        product.setId(productDTO.getId());
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());
        Category category=new Category();
        category.setName(productDTO.getCategory());
        product.setCategory(category);

        return product;
    }

    private FakeStoreProductDTO getFakeStoreProductDTOFromProduct(Product product){
        FakeStoreProductDTO fakeStoreProductDTO=new FakeStoreProductDTO();
        fakeStoreProductDTO.setId(product.getId());
        fakeStoreProductDTO.setTitle(product.getTitle());
        fakeStoreProductDTO.setPrice(product.getPrice());
        fakeStoreProductDTO.setImage(product.getImage());
        fakeStoreProductDTO.setDescription(product.getDescription());
        fakeStoreProductDTO.setCategory(product.getCategory().getName());
        return fakeStoreProductDTO;
    }
}
//FakeStoreProductDto -> product conversion will happen in Service layer
//postForEntity -> forEntity returns ResponseEntity
//postForEntity.getBody()-> returns the object only
//get/patchForObject -> returns the Object only