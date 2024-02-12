package com.example.ProductCategoryService.Models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public abstract class BaseModel {
    private long id;
    private Date CreatedAt;
    private Date LastUpdatedAt;
    private Status status;
}
