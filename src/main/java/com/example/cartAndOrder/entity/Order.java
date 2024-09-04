package com.example.cartAndOrder.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class Order {
    private String oId;
    private String uId;
    private Date date;
    private List<Product> orderList;
    private double totalPrice;
    private int totalItems;
}
