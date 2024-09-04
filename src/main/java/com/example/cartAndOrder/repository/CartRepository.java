package com.example.cartAndOrder.repository;

import com.example.cartAndOrder.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

//import org.springframework.data.mongodb.repository.Update;
@Repository
public interface CartRepository extends MongoRepository<Cart,String> {
//    @Query(value = "{ 'cId': ?0, 'productList.pId': ?1, 'productList.sId': ?2 }",
//            update = "{ '$set': { 'productList.$.quantity': ?3 } }")
//    @Update
//    int updateProductQuantity(String cId, String pId, String sId, int newQuantity);
}
