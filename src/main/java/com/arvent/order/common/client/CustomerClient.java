package com.arvent.order.common.client;

import com.arvent.order.dto.ShoppingCartItemListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient("customer-service")
public interface CustomerClient {

    @GetMapping("/customer/id")
    ResponseEntity getCustomerInfo(@RequestHeader(value = "id") Long id);

    @DeleteMapping("shoppingcart/delete")
    ResponseEntity deleteCustomerCart(@RequestHeader(value = "cartId") List<Long> cartId);

    @DeleteMapping("shoppingcart/deleteItemsByShoppingCartId")
    ResponseEntity deleteItemsByShoppingCartId(@RequestBody List<ShoppingCartItemListDTO> itemList);

}
