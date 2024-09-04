package com.example.cartAndOrder.service;

import com.example.cartAndOrder.RandomIdGenerator;
import com.example.cartAndOrder.entity.Order;
import com.example.cartAndOrder.entity.OrderedProducts;
import com.example.cartAndOrder.entity.Product;
import com.example.cartAndOrder.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;
//add date
    public boolean createOrder(List<OrderedProducts> productList, String cId, Double total){
        Order order=new Order();
        order.setOrderList(productList);
        order.setOId(RandomIdGenerator.generateRandomId());
        order.setUId(cId);
        order.setTotalPrice(total);
        //order.setDate(new Date);
        orderRepository.save(order);
        //add order to user order history
        //calculate total here

        return true;
    }

}
