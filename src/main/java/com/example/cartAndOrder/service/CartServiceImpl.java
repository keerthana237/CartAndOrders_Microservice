package com.example.cartAndOrder.service;

import com.example.cartAndOrder.ApiResponse;
import com.example.cartAndOrder.Dto.CartDto;
import com.example.cartAndOrder.Dto.CartProductDto;
import com.example.cartAndOrder.Dto.ProductDto;
import com.example.cartAndOrder.ProductClientInterface;
import com.example.cartAndOrder.entity.Cart;
import com.example.cartAndOrder.entity.OrderedProducts;
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
    OrderService orderService;

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
        Optional<Cart> optionalCart=cartRepository.findById(cId);
        List<CartProductDto> cartProductDtoList=new ArrayList<>();
        if(optionalCart.isPresent()){
            Cart cart=optionalCart.get();
            List<Product> prodList=cart.getProductList();
            for (Product product : prodList) {
                ResponseEntity<ApiResponse<ProductDto>> responseEntity=productClientInterface.findById(product.getPId());
                ProductDto productDto=responseEntity.getBody().getData();
                System.out.println(productDto);
                CartProductDto cartProductDto=new CartProductDto();
                BeanUtils.copyProperties(product,cartProductDto);
                BeanUtils.copyProperties(productDto,cartProductDto);
                cartProductDto.setCId(cId);
                for (Seller seller : productDto.getSeller()) {
                    if(seller.getSId().equals(product.getSId())){
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
    @Override
    public double getTotalPrice(String cId){
        Optional<Cart> optionalCart=cartRepository.findById(cId);
        double total=0;
        if(optionalCart.isPresent()){
            Cart cart=optionalCart.get();
            if(!cart.getProductList().isEmpty()) {
                List<CartProductDto> li = getItems(cart.getCId());
                for (CartProductDto cartproductDto : li) {
                    double price= cartproductDto.getPrice();
                    total+=(price*cartproductDto.getQuantity());
                }
            }
        }
        return total;
    }
    @Override
    public int getTotalItemCount(String cId){
        Optional<Cart> optionalCart=cartRepository.findById(cId);
        int totalCount=0;
        if(optionalCart.isPresent()){
            Cart cart=optionalCart.get();
            totalCount=cart.getProductList().size();
        }
        return totalCount;
    }
    @Override
    public boolean checkout(String cId) {
        Optional<Cart> optionalCart=cartRepository.findById(cId);
        if(optionalCart.isPresent()){
            Cart cart=optionalCart.get();
            List<CartProductDto> cartProductDtoList=getItems(cId);
            List<OrderedProducts> orderedProductsList=new ArrayList<>();
            for (CartProductDto cartProductDto : cartProductDtoList) {
                productClientInterface.updateStock(cartProductDto.getPId(),cartProductDto.getSId(),cartProductDto.getQuantity());
                OrderedProducts orderedProducts=new OrderedProducts();
                BeanUtils.copyProperties(cartProductDto,orderedProducts);
                orderedProductsList.add(orderedProducts);
            }
            double totalPrice=getTotalPrice(cId);
            if(orderService.createOrder(orderedProductsList,cId,totalPrice)){
                cart.setProductList(null);
                cartRepository.save(cart);
                return true;
            }
        }
        return false;
        //update stock done
        //create order done
        //empty cart content to order done
        //fire notification api?
        //add orderId to user list
    }

}
