package com.example.cartAndOrder.controller;

import com.example.cartAndOrder.ApiResponse;
import com.example.cartAndOrder.Dto.CartProductDto;
import com.example.cartAndOrder.Dto.ProductDto;
import com.example.cartAndOrder.entity.Product;
import com.example.cartAndOrder.repository.CartRepository;
import com.example.cartAndOrder.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cart")
public class CartController {
//    @Autowired
//    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;
    @CrossOrigin
    @PostMapping("/createCart")
    public ResponseEntity<ApiResponse<String>> createCart(@RequestParam String UId){
        cartService.createCart(UId);
        return new ResponseEntity<>(new ApiResponse<>("Cart Created"), HttpStatus.OK);
    }
    @CrossOrigin
    @PostMapping("/addItem")
    public ResponseEntity<ApiResponse<String>> addItem(@RequestBody Product product, @RequestParam String cId){
        if(cartService.addItem(product,cId)){
//            return new ResponseEntity<>(new ApiResponse<>(true,"Item Added"), HttpStatus.OK);
            return new ResponseEntity<>(new ApiResponse<>("Item Added"),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>("Item not Found"),HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin
    @PutMapping("/updateQuantity")
    public ResponseEntity<ApiResponse<String>> updateQuantity(@RequestParam String cId,@RequestParam String pId,@RequestParam String sId,@RequestParam int newQuantity){
        if(cartService.updateQuantity(cId,pId,sId,newQuantity)){
            return new ResponseEntity<>(new ApiResponse<>("Item Quantity Updated"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>("Could not update quantity"),HttpStatus.BAD_REQUEST);
    }
    @CrossOrigin
    @DeleteMapping("/deleteItem")
    public ResponseEntity<ApiResponse<String>> deleteItem(@RequestParam String cId,@RequestParam String pId,@RequestParam String sId){
        if(cartService.deleteItem(cId,pId,sId)){
            return new ResponseEntity<>(new ApiResponse<>("Item Deleted"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>("Item does not Exist"),HttpStatus.BAD_REQUEST);
    }
    @CrossOrigin
    @GetMapping("/getItems")
    public ResponseEntity<ApiResponse<List<CartProductDto>>> getItems(@RequestParam String cId){
        List<CartProductDto> cartProductDtoList=cartService.getItems(cId);
        if(cartProductDtoList==null){
            return new ResponseEntity<>(new ApiResponse<>(false,"No Items Found"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ApiResponse<>(cartProductDtoList),HttpStatus.OK);
    }

//    @CrossOrigin
//    @GetMapping("/getTotal")

}
