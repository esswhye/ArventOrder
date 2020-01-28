package com.arvent.order.service;

import com.arvent.order.dto.ShoppingCartDTO;
import com.arvent.order.dto.ShoppingCartItemListDTO;
import com.arvent.order.entity.Order;
import com.arvent.order.exception.OutOfStockException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order createOrder(ShoppingCartDTO productList) throws OutOfStockException;

    Order getOrderByOrderId(Long userId);

    void deleteOrderByCustomerId(Long orderId);

    @Transactional
    void updateProductQuantityTest(ShoppingCartDTO shoppingCartDTO) throws OutOfStockException;

    void changeOrderToShip(Order order, List<ShoppingCartItemListDTO> itemList);
}
