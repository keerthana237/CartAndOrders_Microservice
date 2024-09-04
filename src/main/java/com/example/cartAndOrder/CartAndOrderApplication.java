package com.example.cartAndOrder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
public class CartAndOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartAndOrderApplication.class, args);
	}

//	@FeignClient("name")
//	static interface NameService {
//		@RequestMapping("/")
//		public String getName();
}

