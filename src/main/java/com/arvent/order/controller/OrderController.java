package com.arvent.order.controller;

import com.arvent.order.dto.ShoppingCartDTO;
import com.arvent.order.exception.OutOfStockException;
import com.arvent.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arvent.order.entity.Order;

@RestController
@RequestMapping(name = "order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;


    @ApiOperation(value = "Create Order")
    @PostMapping("/create")
    public ResponseEntity createOrder(@RequestBody ShoppingCartDTO shoppingCartDTO) throws OutOfStockException {

        /**
         * UpdateProductQuantity
         * Payment
         * Successful
         * createOrder with status To_Ship - delete shoppingcart item - and  -block quantity add purchasePrice
         * --------------------------------
         * Failed
         * createOrder with status To_Pay expire in 1hour
         */

        orderService.updateProductQuantityTest(shoppingCartDTO);
        //TODO - What if Product-service suddenly crash?
        Order order = orderService.createOrder(shoppingCartDTO);
        //TODO - Payment Success
        orderService.changeOrderToShip(order,shoppingCartDTO.getItemList());
        //TODO - Payment Fail
        //add expire

        /*
        try {
            orderService.createOrder(shoppingCartDTO);
        }catch Exception(){
            //send a msg to mq, worker do it
        }
        */
        boolean paymentSuccess = true;
        /*
        if(orderService.paymentCharge())
        {

        }
        */
        return new ResponseEntity<>("Purchase Successfully", HttpStatus.OK);

    }

    @ApiOperation(value = "Get Order by Order Id")
    @GetMapping("/get")
    public ResponseEntity getOrderByOrderId(@RequestHeader Long orderId){


        Order order = orderService.getOrderByOrderId(orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @ApiOperation(value = "Get Order by Order Id")
    @GetMapping("/delete")
    public ResponseEntity deleteOrderByCustomerId(@RequestHeader Long orderId){


        orderService.deleteOrderByCustomerId(orderId);

        return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);

    }

}