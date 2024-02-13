package com.example.ProductCategoryService.Client.Fakestore.DTOs;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FakeStoreProductDTO {
    private long id;
    private String title;
    private String description;
    private String image;
    private double price;
    private String category;//category of a product
    private FakeStoreRatingDTO ratingDTO;
}
