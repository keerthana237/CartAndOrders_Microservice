package com.example.cartAndOrder.service;

import com.example.cartAndOrder.RandomIdGenerator;
import com.example.cartAndOrder.entity.Order;
import com.example.cartAndOrder.entity.Product;
import com.example.cartAndOrder.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    public boolean createOrder(List<Product> productList, String cId,Double total){
        Order order=new Order();
        order.setOrderList(productList);
        order.setOId(RandomIdGenerator.generateRandomId());
        order.setUId(cId);
        order.setTotalPrice(total);
        orderRepository.save(order);
        //add order to user order history
        //calculate total here

        return true;
    }

}
