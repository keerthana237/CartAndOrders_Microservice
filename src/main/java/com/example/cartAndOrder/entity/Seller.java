package com.example.cartAndOrder.entity;

import lombok.Data;

@Data
public class Seller {
    private String sName;
    private String sId;
    private int stock;
    private double price;
}