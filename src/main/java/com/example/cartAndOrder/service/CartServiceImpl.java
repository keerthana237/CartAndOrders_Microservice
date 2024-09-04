package com.example.cartAndOrder.service;

import com.example.cartAndOrder.ApiResponse;
import com.example.cartAndOrder.Dto.CartDto;
import com.example.cartAndOrder.Dto.CartProductDto;
import com.example.cartAndOrder.Dto.ProductDto;
import com.example.cartAndOrder.ProductClientInterface;
import com.example.cartAndOrder.entity.Cart;
import com.example.cartAndOrder.entity.Product;
import com.example.cartAndOrder.entity.Seller;
import com.example.cartAndOrder.repository.CartRepository;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements  CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductClientInterface productClientInterface;

    @Override
    public void createCart(String uId){
        Cart cart=new Cart();
        cart.setCId(uId);
        List<Product> li =new ArrayList<>();
        cart.setProductList(li);
        cartRepository.save(cart);
    }

    @Override
    public boolean addItem(Product product, String cId){
        System.out.println(product);
        Optional<Cart> optionalCart=cartRepository.findById(cId);
        if(optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            List<Product> li = new ArrayList<>();
            if (cart.getProductList() != null) {
                li = cart.getProductList();
            }
            li.add(product);
            cart.setProductList(li);
            cartRepository.save(cart);
            return true;
        }
        return false;
    }

    public List<CartProductDto> getItems(String cId){
        System.out.println("inside getItems");
        Optional<Cart> optionalCart=cartRepository.findById(cId);
        List<CartProductDto> cartProductDtoList=new ArrayList<>();
        if(optionalCart.isPresent()){
            Cart cart=optionalCart.get();
            List<Product> prodList=cart.getProductList();
            for (Product product : prodList) {
                System.out.println("beforeFeign");
                ResponseEntity<ApiResponse<ProductDto>> responseEntity=productClientInterface.findById("dedd06aa-7b5e-4bc0-99a1-cac863fbe6e9");
                System.out.println("response:"+responseEntity);
                System.out.println("afterfeign");
                ProductDto productDto=responseEntity.getBody().getData();
                System.out.println(productDto);
                CartProductDto cartProductDto=new CartProductDto();
                BeanUtils.copyProperties(productDto,cartProductDto);
                BeanUtils.copyProperties(product,cartProductDto);
                for (Seller seller : productDto.getSeller()) {
                    if(seller.getSId().equals("SELLER002")){
                        BeanUtils.copyProperties(seller,cartProductDto);
                    }
                }
                cartProductDtoList.add(cartProductDto);
            }
        }
        return cartProductDtoList;
    }

    @Override
    public boolean updateQuantity(String cId,String pId,String sId,int newQuantity){
        Optional<Cart> optionalCart=cartRepository.findById(cId);
        if(optionalCart.isPresent()){
            Cart cart=optionalCart.get();
            for (Product product : cart.getProductList()) {
                if(product.getPId().equals(pId) && product.getSId().equals(sId)){
                    product.setQuantity(newQuantity);
                    cartRepository.save(cart);
                    return true;
                }
            }
        }
        return  false;
    }


//    public boolean updateQuantity(String cId, String pId, String sId, int newQuantity) {
//        int updateResult = cartRepository.updateProductQuantity(cId, pId, sId, newQuantity);
//        return updateResult > 0;
//    }
    @Override
    public boolean deleteItem(String cId,String pId,String sId){
        Optional<Cart> optionalCart=cartRepository.findById(cId);
        if(optionalCart.isPresent()){
            Cart cart=optionalCart.get();
            for (Product product : cart.getProductList()) {
                if (product.getPId().equals(pId) && product.getSId().equals(sId)) {
                    List<Product> li=cart.getProductList();
                    li.remove(product);
                    cart.setProductList(li);
                    cartRepository.save(cart);
                    return true;
                }

            }

        }
        return false;
    }

    public double getTotal(String cId){
        Optional<Cart> optionalCart=cartRepository.findById(cId);
        double total=0;
        if(optionalCart.isPresent()){
            Cart cart=optionalCart.get();
            if(!cart.getProductList().isEmpty()) {
                List<CartProductDto> li = getItems(cart.getCId());
                for (CartProductDto cartproductDto : li) {
                    total += cartproductDto.getPrice();
                }
            }
        }
        return total;
    }

//    public boolean checkoutCart(String cId) {
//
//
//
//        //update stock
//        //create order
//        //empty cart content to order
//        //fire notification api?
//        //add orderId to user list
//
//
//
//
//    }

}
