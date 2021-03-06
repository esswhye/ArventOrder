package com.arvent.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingCartItemListDTO {

    private Long cartId;
    private Long productId;
    private String productUrl;
    private String productBrand;
    private double productPrice;
    private double subTotal;
    private int quantity;
}
