package com.arvent.order.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private Long id;

    private String productName;

    private String productBrand;

    private Double productPrice;

    private Double productDiscount;

    private String productImageLink;

    private ProductHeightWidth productHeightWidth;

    private int availableQuantity;

    private int blockQuantity;
}

