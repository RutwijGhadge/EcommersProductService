package com.example.ProductCategoryService.Services;

import com.example.ProductCategoryService.Client.Fakestore.Client.FakeStoreApiClient;
import com.example.ProductCategoryService.Client.Fakestore.DTOs.FakeStoreProductDTO;
import com.example.ProductCategoryService.DTOs.ProductDTO;
import com.example.ProductCategoryService.Models.Category;
import com.example.ProductCategoryService.Models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;


@Service
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
    public Product getProduct(long productID) {//get the particular/Specific details of the product with id
       // RestTemplate restTemplate=restTemplateBuilder.build();
       // FakeStoreProductDTO fakeStoreProductDTO=restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDTO.class,productID).getBody();
        //conversion logic of ProductDto to Product
        FakeStoreProductDTO fakeStoreProductDTO=fakeStoreApiClient.getProduct(productID);
        return getProduct(fakeStoreProductDTO);
    }

    @Override
    public Product createProduct(Product product) {//create / add the Product
      //  RestTemplate restTemplate=restTemplateBuilder.build();
      //  FakeStoreProductDTO fakeStoreProductDTO=restTemplate.postForEntity("https://fakestoreapi.com/products",product,FakeStoreProductDTO.class).getBody();
        FakeStoreProductDTO fakeStoreProductDTO=getFakeStoreProductDTOFromProduct(product);
        return getProduct(fakeStoreApiClient.createProduct(fakeStoreProductDTO));
    }

 /*   public <T> ResponseEntity<T> postForEntity(String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate
        RequestCallback requestCallback = this.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = this.responseEntityExtractor(responseType);
        return (ResponseEntity)nonNull((ResponseEntity)this.execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables));
    }
*/

    @Override
    public Product updateProduct(Long id, Product product) {//update a Product
        RestTemplate restTemplate=restTemplateBuilder.build();
        FakeStoreProductDTO fakeStoreProductDTO=restTemplate.patchForObject("https://fakestoreapi.com/{id}", product , FakeStoreProductDTO.class,id);
        return getProduct(fakeStoreProductDTO);
    }

    private Product getProduct(FakeStoreProductDTO productDTO){
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