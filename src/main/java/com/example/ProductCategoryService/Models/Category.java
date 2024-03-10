package com.example.ProductCategoryService.Models;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel{
    private String name;
    private String description;
    @OneToMany(mappedBy = "category" ,fetch =FetchType.EAGER)
    private List<Product> product;
}
