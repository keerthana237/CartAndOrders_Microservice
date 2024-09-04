package com.example.cartAndOrder;

import com.example.cartAndOrder.Dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ProductService", url = "http://10.20.3.216:8090", path = "/product")
public interface ProductClientInterface {
    @GetMapping("/{pid}")
    public ResponseEntity<ApiResponse<ProductDto>> findById(@PathVariable("pid") String pid);
}
