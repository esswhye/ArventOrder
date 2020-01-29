package com.arvent.order.client;

import com.arvent.order.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@FeignClient("product-service")
public interface ProductClient {

    @GetMapping("product/id")
    ResponseEntity <Product>getProductById(@RequestParam("productId") Long productId);

    @PostMapping("/product/updateProductQuantity")
    ResponseEntity updateProductQuantity(@RequestBody HashMap<Long, Integer> productIdQuality);

    @PostMapping("/product/resetProductQuantity")
    ResponseEntity updateProductQuantityBack(@RequestBody Map<Long, Integer> productsIdQuality);

}
