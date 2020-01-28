package com.arvent.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingCartDTO {

    private List<ShoppingCartItemListDTO> itemList;

    private String customerName;

    private Long customerId;

    private double totalCost;

}
