package com.example.cartAndOrder.service;

import com.example.cartAndOrder.Dto.CartProductDto;
import com.example.cartAndOrder.Dto.ProductDto;
import com.example.cartAndOrder.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    public void createCart(String uId);
    public boolean addItem(Product product, String cId);
    public boolean updateQuantity(String cId,String pId,String sId,int newQuantity);
    public boolean deleteItem(String cId,String pId,String sId);
    public List<CartProductDto> getItems(String cId);
    public double getTotalPrice(String cId);
    public int getTotalItemCount(String cId);
    public boolean checkout(String cId);


    }
