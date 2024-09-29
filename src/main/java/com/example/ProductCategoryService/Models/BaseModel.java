package com.example.ProductCategoryService.Models;

import lombok.Getter;
import lombok.Setter;


import jakarta.persistence.*;


import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date CreatedAt;
    private Date LastUpdatedAt;
    private Status status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    public Date getLastUpdatedAt() {
        return LastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        LastUpdatedAt = lastUpdatedAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseModel baseModel = (BaseModel) o;
        return id == baseModel.id && Objects.equals(CreatedAt, baseModel.CreatedAt) && Objects.equals(LastUpdatedAt, baseModel.LastUpdatedAt) && status == baseModel.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, CreatedAt, LastUpdatedAt, status);
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + id +
                ", CreatedAt=" + CreatedAt +
                ", LastUpdatedAt=" + LastUpdatedAt +
                ", status=" + status +
                '}';
    }
}
