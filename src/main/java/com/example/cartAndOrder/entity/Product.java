package com.example.cartAndOrder.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class Product
{
    private String pId;
    private String sId;
    private int quantity;
}
