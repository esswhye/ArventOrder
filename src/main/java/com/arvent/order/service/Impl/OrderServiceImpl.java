package com.arvent.order.service.Impl;

import com.arvent.order.client.CustomerClient;
import com.arvent.order.client.ProductClient;
import com.arvent.order.dto.Product;
import com.arvent.order.dto.ShoppingCartDTO;
import com.arvent.order.dto.ShoppingCartItemListDTO;
import com.arvent.order.entity.Order;
import com.arvent.order.entity.OrderItem;
import com.arvent.order.entity.Status;
import com.arvent.order.repository.OrderRepository;
import com.arvent.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService
{
    private OrderRepository orderRepository;

    private ProductClient productService;

    private CustomerClient customerService;

    //private OrderItemRepository orderItemRepository;

   // private ShoppingCartClient shoppingCartService;

    @Override
    public List<Order> getAllOrders() {

        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(ShoppingCartDTO shoppingCartDTO)  {

        Order order;
        List<OrderItem> orderItemList = new ArrayList<>();

        //ResponseEntity res = customerService.getCustomerInfo(shoppingCartDTO.getCustomerId());

        order = Order.builder().currentStatus(Status.TOPAY.getStatus())
                .customerId(shoppingCartDTO.getCustomerId())
                .orderItemList(orderItemList).totalCost(0.0).build();

        //order = Order.builder().currentStatus(Status.TOPAY.toString()).customer(itemList.get(0).getCustomer()).totalCost(totalCost).orderItemList(orderItemList).build();

        for (ShoppingCartItemListDTO item:shoppingCartDTO.getItemList()
        ) {

            Product product = productService.getProductById(item.getProductId()).getBody();

            OrderItem orderItem = new OrderItem(item.getProductId(), item.getQuantity());

            orderItem.setOrder(order);
            orderItemList.add(orderItem);
            orderItem.setPurchasedProductPrice(product.getProductPrice());
            orderItem.setSubTotal(product.getProductPrice()*item.getQuantity());
            order.addTotalCost(product.getProductPrice(),item.getQuantity());
        }

        Order orderSaved = orderRepository.save(order);

        //shoppingCartService.deleteItemsByShoppingCartId(shoppingCartDTO.getItemList());

        //Remove this after finishing payment



        return orderSaved;

    }

    @Override
    public Order getOrderByOrderId(Long userId) {

        return orderRepository.findById(userId).get();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteOrderByCustomerId(Long orderId) {

        //Product product = orderRepository.getById(cartId);

        Order order = orderRepository.findById(orderId).get();

        Map<Long,Integer> itemIdQuantity = order.getOrderItemList().stream().collect(Collectors.toMap(OrderItem::getProductId, t-> t.getQuantity()));

        productService.updateProductQuantityBack(itemIdQuantity);

        orderRepository.deleteById(orderId);

    }

    @Override
    @Transactional
    public void updateProductQuantityTest(ShoppingCartDTO shoppingCartDTO) {
        //List<Long> productIdList = shoppingCartDTO.getItemList().stream().map(ShoppingCartItemListDTO::getProductId).collect(Collectors.toList());

        HashMap<Long,Integer> productIdQuantity = new HashMap<>();

        shoppingCartDTO.getItemList().forEach(item ->
                productIdQuantity.put(item.getProductId(),item.getQuantity()));

        productService.updateProductQuantity(productIdQuantity);

    }

    @Override
    public void changeOrderToShip(Order order, List<ShoppingCartItemListDTO> itemList) {

        order.setCurrentStatus(Status.TOSHIP.getStatus());
        orderRepository.save(order);
        customerService.deleteItemsByShoppingCartId(itemList);

    }


}