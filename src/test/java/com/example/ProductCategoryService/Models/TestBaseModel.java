package com.example.ProductCategoryService.Models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestBaseModel {

    @Test
    @DisplayName(("Pojo Getter Setter Test"))
    void testAsPojo(){
        BaseModel baseModel=new BaseModel();
        baseModel.setId(1);
        baseModel.setCreatedAt(Date.valueOf("2024-11-10"));
        baseModel.setLastUpdatedAt(baseModel.getCreatedAt());
        baseModel.setStatus(Status.Active);

        BaseModel baseModel1=new BaseModel();
        baseModel1.setId(baseModel.getId());
        baseModel1.setStatus(baseModel.getStatus());
        baseModel1.setCreatedAt(baseModel.getCreatedAt());
        baseModel1.setLastUpdatedAt(baseModel.getLastUpdatedAt());
        assertEquals(baseModel,baseModel1);
    }

    @Test
    @DisplayName("Test ToString and HashCode")
    void testToString(){
        BaseModel baseModel=new BaseModel();
        baseModel.setId(1);
        baseModel.setCreatedAt(Date.valueOf("2024-11-10"));
        baseModel.setLastUpdatedAt(baseModel.getCreatedAt());
        baseModel.setStatus(Status.Active);
        assertEquals("BaseModel{id=1, CreatedAt=2024-11-10, LastUpdatedAt=2024-11-10, status=Active}",
                baseModel.toString());
        BaseModel baseModel1=baseModel;
        assertEquals(baseModel.hashCode(),baseModel1.hashCode());
    }
}
