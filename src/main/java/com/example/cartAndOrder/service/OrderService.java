package com.example.cartAndOrder.service;

import com.example.cartAndOrder.entity.OrderedProducts;
import com.example.cartAndOrder.entity.Product;

import java.util.List;

public interface OrderService {
    public boolean createOrder(List<OrderedProducts> productList, String cId, Double total);

    }
